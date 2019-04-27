package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.LanguageBO;

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
    List<LanguageBO> languageDetails();
    /**
     *  课时数
     * @param levelId  级别id
     * @return 数量 可能为null
     */
    Integer chapterCount(Integer levelId);
}
