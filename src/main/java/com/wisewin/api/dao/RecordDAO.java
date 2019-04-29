package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.RecordBO;

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
     * @param userId 用户id
     * @return
     */
    Integer selectUserRecord(Integer userId);
}
