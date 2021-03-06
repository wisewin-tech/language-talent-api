package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LanguageDAO {
    //查询语言国旗图片
    List<LanguageBO> selectEnsignImage();

    //限时特惠
    List<FlashSalesResultBO> getFlashSales();

    /**
     * 获取全部限时特惠
     * @return
     */
    List<FlashSalesResultBO> getAllFlashSales();

    /**
     * 查询语言
     * @param id
     * @return
     */
    LanguageBO selectLanguage(Integer id);

    /**
     * 语言详情页语言模块
     * @return
     */
    LanguageDetailsResultBO languageDetails(Integer id);
    //语言详情页课程模块
    List<LanguageDetailsCourseResultBO> languageDetailsCourse(Integer id);

    /**
     * 我学习的语言
     * @param userId
     * @return
     */
    List<MyStudyLanguageBO> myStudyLanguage(Integer userId);

    /**
     * 获取语言名称列表
     * @return
     */
    List<MyStudyLanguageBO> languageList();


    /**
     * 单查询语言
     */
    LanguageBO selectLanguageG(@Param("id") String id);



}
