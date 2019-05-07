package com.wisewin.api.service;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.dao.FavoritesDAO;
import com.wisewin.api.entity.bo.FavoritesResultBO;
import com.wisewin.api.entity.bo.MyFavoriteBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FavoritesService {
    @Resource
    private FavoritesDAO favoritesDAO;

    /**
     * 查询我的收藏,map封装了用户id,页数,每页行数
     * @param map
     * @return
     */
    public List<FavoritesResultBO> selectAll(Map<String,Object> map){
        Object source=map.get("source");
        if (source.equals(UserConstants.CHAPTER.getValue())){
            return favoritesDAO.selectFavorites( map);
        }else{
            return favoritesDAO.selectDiscover(map);
        }
    }

    /**
     * 添加收藏  Source---收藏来源(这里为课时的收藏)
     * @param userId    用户id
     * @param sourceId  收藏来源id
     */
    public void insertCollect(Integer userId,Integer sourceId,String source ){
        //创建 收藏表对象,把用户id和收藏来源id放进去
        MyFavoriteBO favoriteBO=new MyFavoriteBO(userId,sourceId);
        //设置收藏来源用户传过来的值(课时/发现)
        favoriteBO.setSource(source);
        //添加收藏
        favoritesDAO.insertCollect(favoriteBO);
    }


    /**
     * 取消收藏
     * @param userId
     */
    public void delCollect(Integer userId,Integer sourceId){
        //创建 收藏表对象,把用户id和收藏来源id放进去
        MyFavoriteBO favoriteBO=new MyFavoriteBO(userId,sourceId);
        favoritesDAO.delCollect(favoriteBO);
    }


}
