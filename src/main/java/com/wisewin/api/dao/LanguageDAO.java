package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.LanguageBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LanguageDAO {
    //查询语言国旗图片
    List<LanguageBO> selectEnsignImage();

    //限时特惠
    List<LanguageBO> getFlashSales();

    /**
     * 查询语言
     * @param id
     * @return
     */
    LanguageBO selectLanguage(Integer id);

    /**
     * 语言详情页
     * @return
     */
    List<LanguageBO> languageDetails(Integer id);

    /**
     * 我学习的语言
     * @param userId
     * @return
     */
    List<LanguageBO> myStudyLanguage(Integer userId);

    /**
     * 获取语言名称列表
     * @return
     */
    List<LanguageBO> languageList();


    /**
     * 单查询语言
     */
    LanguageBO selectLanguageG(@Param("id") String id);


}
