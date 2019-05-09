package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.CouplebackBO;

import java.util.List;

/**
 * 反馈信息
 */
public interface CouplebacDAO {

    /**
     * 添加反馈信息
     * String content; //反馈内容
     * String contactWay//什么方式
     * String contactpattern; //用户联系方式
     * String pictureUrl; //图片url
     */
    Integer  addCpupleback(CouplebackBO couplebackBO);


}
