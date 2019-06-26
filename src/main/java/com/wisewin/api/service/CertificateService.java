package com.wisewin.api.service;

import com.wisewin.api.dao.CertificateDAO;
import com.wisewin.api.entity.bo.CateBO;
import com.wisewin.api.entity.bo.CertificateResultBO;
import com.wisewin.api.entity.bo.UserBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("certificateService")
@Transactional
public class CertificateService {

    @Resource
    private CertificateDAO certificateDAO;

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
    public String getCertificateNumber(){
        //证书编号
        String certificateNumber="DYK";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMdd");
        return certificateNumber=certificateNumber+simpleDateFormat.format(new Date());
    }



}
