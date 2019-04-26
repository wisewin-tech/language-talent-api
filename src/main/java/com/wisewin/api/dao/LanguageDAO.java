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
}
