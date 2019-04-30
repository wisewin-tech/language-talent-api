package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.FavoritesResultBO;
import com.wisewin.api.entity.bo.MyFavoriteBO;

import java.util.List;
import java.util.Map;

public interface FavoritesDAO {
    /**
     * 查询我的收藏
     * @param map 用于存放用户id,第几页,每页行数
     * @return
     */
    List<FavoritesResultBO> selectFavorites(Map<String,Object> map);

    /**
     * 添加收藏
     * @param favoriteBO
     */
    void insertCollect(MyFavoriteBO favoriteBO);

}
