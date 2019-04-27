package com.wisewin.api.service;

import com.wisewin.api.dao.LanguageDAO;
import com.wisewin.api.entity.bo.LanguageBO;
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
    public List<LanguageBO> getFlashSales(){

        return languageDAO.getFlashSales();
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
     * 语言详情
     * @return
     */
    public List<LanguageBO> languageDetails(){
        List<LanguageBO> languageBOList = languageDAO.languageDetails();
        for (LanguageBO languageBO:languageBOList){
              Integer  sum=languageDAO.chapterCount(languageBO.getLevelId());
              languageBO.setChapterCount(sum);
        }


        return languageBOList;
    }
}
