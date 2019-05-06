package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.RecordBO;
import com.wisewin.api.entity.bo.UserSignBO;

import java.util.List;
import java.util.Map;

/**
 * 记录表持久层
 */
public interface RecordDAO {
    /**
     * 添加一个用户行为记录
     * 来源可以是积分,咖豆或者礼品卡
     * 类型可以使增加或是减少
     * @param recordBO
     */
    void insertUserAction(RecordBO recordBO);

    List<RecordBO> selectUserAction(Map<String,Object> map);

    /**
     * 查询用户记录总条数
     * map封装用户id和来源
     * 来源可以是积分,咖豆或者礼品卡
     * @param map 用户id---- 来源是积分
     * @return
     */
    Integer selectUserRecord(Map<String, Object> map);
    //修改用户表积分咖豆信息
    void updateUser(UserSignBO userBO);
}
