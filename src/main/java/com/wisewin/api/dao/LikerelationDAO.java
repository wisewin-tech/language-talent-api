package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.LikerelationBO;

/**
 * 发现内容喜欢
 */
public interface LikerelationDAO {

    /**
     * 根据发现id和用户id来查找
     * Integer userId; //用户id
     * Integer dcId; //发现id
     */
    LikerelationBO findLikerelation(LikerelationBO likerelationBO);


    /**
     * 添加
     * 如果用户要是点击喜欢如果在喜欢关系表里面没有查询出记录，说明它以前没有点击过，
     * 所以可以进行喜欢，喜欢同时，需要添加，文章id和用户id进行记录，记录说明这个用户喜欢过这个文章
     */
    Integer addLikerelation(LikerelationBO likerelationBO);

    /**
     * 删除用户喜欢文章
     */
    Integer deleteLikerelation(Integer id);

    /**
     * 获取id
     */
    LikerelationBO findLikerelationjson(LikerelationBO likerelationBO);



}
