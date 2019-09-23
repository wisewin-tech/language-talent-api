package com.wisewin.api.service;

import com.wisewin.api.dao.CertificateDAO;
import com.wisewin.api.entity.bo.*;
import com.wisewin.api.util.DateUtils;
import com.wisewin.api.util.RandomUtil;
import com.wisewin.api.util.redisUtils.RedissonHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("certificateService")
@Transactional
public class CertificateService {

    @Resource
    private CertificateDAO certificateDAO;
    @Resource
    private LanguageService languageService;
    public String certificateImage(Integer certificateId){

        return certificateDAO.certificateImage(certificateId);
    }

    /**
     * 查询用户证书
     * @param userId
     * @return
     */
    public List<CateBO> queryCateList(String userId){
        System.out.println(certificateDAO.queryCateList(userId));
        return certificateDAO.queryCateList(userId);
    }
    /**
     * 获取证书编号
     */
    public String getCertificateNumber() throws ParseException {
        String key="CertificateNumber";
        Object number=RedissonHandler.getInstance().get(key);
        if(number==null){
            number="1";
        }
        RedissonHandler.getInstance().set(key , new Integer(number.toString())+1 , DateUtils.getMorningMS());

        int length=5-number.toString().length();
        String number0=RandomUtil.generateZeroString(length);
        number=number0+number;


        //证书编号
        String certificateNumber="DYK";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMdd");


        certificateNumber=certificateNumber+simpleDateFormat.format(new Date())+number;
        return certificateNumber;
    }


    /**
     * new 查询用户证书
     */
    public List<QCertificateBO> queryCertificate(Integer userId) {
        List<QCertificateBO> qCertificateBOS = certificateDAO.queryCertificate(userId);
        if(qCertificateBOS!=null && qCertificateBOS.size()>0){
            for(QCertificateBO bo:qCertificateBOS){
                String s = certificateDAO.queryCourseName(bo.getId());
                bo.setBeStudying(s);
            }
        }

        return qCertificateBOS;
    }

    /**
     * 证书详情页
     * @param languageId
     * @param courseId
     */
    public CertificateMsgBO  certificateDetails(Integer languageId, Integer courseId,Integer userId) {
        // 未传语言id 获取语言id
        if(languageId==null){
            languageId= certificateDAO.queryLanguageId(courseId);
        }
        LanguageDetailsResultBO language= languageService.languageDetails(languageId);
        CertificateMsgBO  msg =new CertificateMsgBO();
        msg.setImageUrl(language.getThumbnailImageUrl());
        msg.setLanguageId(language.getId());
        msg.setLanguageName(language.getLanguageName());

        List<CourseCerBO> courseCerBOS = certificateDAO.queryCourseCer(language.getId());
        if(courseCerBOS!=null && courseCerBOS.size() >0){
            for(int i=0;i<courseCerBOS.size();i++){
                CourseCerBO courseCerBO = courseCerBOS.get(i);
                List<LevelCerBO> levelCerBOS = certificateDAO.queryLevelCer(courseCerBO.getCourseId());
                if(levelCerBOS!=null && levelCerBOS.size()>0) {
                    for(LevelCerBO levelCerBO:levelCerBOS){
                        BigDecimal bigDecimal = certificateDAO.queryScore(userId, levelCerBO.getLevelId());
                        if(bigDecimal==null || bigDecimal.compareTo(new BigDecimal("0"))==0 ){
                            levelCerBO.setScore(new BigDecimal("0"));
                        }else{
                            Integer integer = certificateDAO.queryChaperCount(levelCerBO.getLevelId());
                            if(integer==null || integer==0){
                                integer=1;
                            }
                            levelCerBO.setScore(bigDecimal.divide(new BigDecimal(integer),2,BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }
                courseCerBO.setLevels(levelCerBOS);
            }

        }
        msg.setCourses(courseCerBOS);

        return msg;
    }

    /**
     * 判断语言有没有证书
     * @param languageId
     * @param courseId
     * @return
     */
    public boolean  isFicate(Integer languageId, Integer courseId) {
        if(languageId==null){
            languageId= certificateDAO.queryLanguageId(courseId);
        }
        return  certificateDAO.queryNot(languageId)>0;
    }
}
