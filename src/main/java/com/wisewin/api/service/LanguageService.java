package com.wisewin.api.service;

import com.wisewin.api.dao.LanguageDAO;
import com.wisewin.api.entity.bo.FlashSalesResultBO;
import com.wisewin.api.entity.bo.LanguageBO;
import com.wisewin.api.entity.bo.LanguageDetailsCourseResultBO;
import com.wisewin.api.entity.bo.LanguageDetailsResultBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("languageService")
@Transactional
public class LanguageService {
    @Resource
    private LanguageDAO languageDAO;

    /**
     * 查询语言国旗图片
     * @return
     */
    public List<LanguageBO> selectEnsignImage(){
        return languageDAO.selectEnsignImage();
    }

    /**
     * 限时特惠
     * @return
     */
    public List<FlashSalesResultBO> getFlashSales(){

        return languageDAO.getFlashSales();
    }

    /**
     * 获取全部限时特惠
     * @return
     */
    public List<FlashSalesResultBO> getAllFlashSales(){
        return languageDAO.getAllFlashSales();
    }
     /**
     * 查询语言
     * @param id
     * @return
     */
    public LanguageBO selectLanguage(Integer id){
        return languageDAO.selectLanguage(id);
    }

    /**
     * 语言详情语言模块
     * @return
     */
    public LanguageDetailsResultBO languageDetails(Integer id){
        LanguageDetailsResultBO languageBOList = languageDAO.languageDetails(id);
        return languageBOList;
    }
    /**
     * 语言详情课程模块
     * @return
     */
    public List<LanguageDetailsCourseResultBO> languageDetailsCourse(Integer id){
        return languageDAO.languageDetailsCourse(id);
    }

    /**
     * 我学习的语言
     * @param userId 用户id
     * @return
     */
    public List<LanguageBO> myStudyLanguage(Integer userId){
        return languageDAO.myStudyLanguage(userId);
    }

    /**
     * 语言名称列表
     * @return
     */
    public List<LanguageBO> languageList(){
        return languageDAO.languageList();
    }
}
