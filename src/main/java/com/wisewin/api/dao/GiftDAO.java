package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.GiftBO;
import com.wisewin.api.entity.bo.GiftRecordBO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 礼品卡兑换
 */
public interface GiftDAO {

    /**
     * 首先前端传个礼品卡兑换码过来进行查找，查找是否有礼品卡，
     * 如果有的话
     * 进行判断状态是否可用
     * 进行判断礼品卡如果大于当前时间说明已到期，如果小于起始时间说明未开始就进行兑换
     * 1，首先在兑换记录表里面添加兑换记录，
     * 修改兑换码卡号状态
     * 修改咖豆
     */


    /**
     * 根据礼品卡码查找是否存在
     * exchangeyard 兑换码
     */
    GiftBO  queryGift(@Param("exchangeyard")String exchangeyard);


    /**
     * 在礼品卡记录表添加记录
     * Integer id; //礼品卡id
     * Integer id; //当前用户id
     * Integer value; //兑换值
     */
    Integer addGiftrecord(GiftRecordBO giftRecordBO);

    /**
     * 修改礼品卡状态
     * String status  状态
     */
    Integer updateGift(Map<String,Object> map);

    /**
     * 修改咖豆数量
     * Integer id 用户id
     * Integer currency 咖豆
     */
    Integer updateUserGift(Map<String,Object> map);




}
