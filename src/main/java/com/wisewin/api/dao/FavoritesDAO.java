package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.DiscoverResultBO;
import com.wisewin.api.entity.bo.FavoritesResultBO;
import com.wisewin.api.entity.bo.MyFavoriteBO;
import com.wisewin.api.entity.bo.SpecialResultBO;

import java.util.List;
import java.util.Map;

public interface FavoritesDAO {
    /**
     * 查询我的收藏(课时)
     * @param map 用于存放用户id,第几页,每页行数,source状态课时
     * @return
     */
    List<FavoritesResultBO> selectFavorites(Map<String,Object> map);
    /**
     * 查询我的收藏(发现)
     * @param map 用于存放用户id,第几页,每页行数,source状态发现
     * @return
     */
    List<DiscoverResultBO> selectDiscover(Map<String,Object> map);

    /**
     * 查询我的收藏(专题)
     * @param map
     * @return
     */
    List<SpecialResultBO> selectSubject(Map<String,Object> map);

    /**
     * 添加收藏
     * @param favoriteBO
     */
    void insertCollect(MyFavoriteBO favoriteBO);
    /**
     * 取消收藏
     * @param favoriteBO
     */
    void delCollect(MyFavoriteBO favoriteBO);

    /**
     * 查询用户收藏信息
     * @param favoriteBO
     * @return
     */
    Integer selectAll(MyFavoriteBO favoriteBO);


}
