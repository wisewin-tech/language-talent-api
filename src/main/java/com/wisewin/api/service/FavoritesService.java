package com.wisewin.api.service;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.dao.FavoritesDAO;
import com.wisewin.api.entity.bo.DiscoverResultBO;
import com.wisewin.api.entity.bo.FavoritesResultBO;
import com.wisewin.api.entity.bo.MyFavoriteBO;
import com.wisewin.api.entity.bo.SpecialResultBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FavoritesService {
    @Resource
    private FavoritesDAO favoritesDAO;

    /**
     * 查询课时收藏,map封装了用户id,页数,每页行数
     * @param map
     * @return
     */
    public List<FavoritesResultBO> selectHour(Map<String,Object> map){
        map.put("source",UserConstants.CHAPTER.getValue());
        return favoritesDAO.selectFavorites( map);
    }
    /**
     * 因结果集不同,无法合并为一个方法
     * 查询发现收藏,map封装了用户id,页数,每页行数
     * @param map
     * @return
     */
    public List<DiscoverResultBO> selectDiscover(Map<String,Object> map){
            map.put("source",UserConstants.DISCOVERY.getValue());
            return favoritesDAO.selectDiscover(map);

    }
    /**
     * 因结果集不同,无法合并为一个方法
     * 查询发现收藏,map封装了用户id,页数,每页行数
     * @param map
     * @return
     */
    public List<SpecialResultBO> selectSubject(Map<String,Object> map){
        map.put("source",UserConstants.SUBJECT.getValue());
        return favoritesDAO.selectSubject(map);

    }

    /**
     * 添加收藏  Source---收藏来源(这里为课时的收藏)
     * @param userId    用户id
     * @param sourceId  收藏来源id
     */
    public boolean insertCollect(Integer userId,Integer sourceId,String source ){
        //创建 收藏表对象,把用户id和收藏来源id放进去
        MyFavoriteBO favoriteBO=new MyFavoriteBO(userId,sourceId,source);
       Integer i=favoritesDAO.selectAll(favoriteBO);
       if (i>0){  //如果收藏表已经有这个记录
           favoritesDAO.delCollect(favoriteBO);
           return false;
       }else{
           favoritesDAO.insertCollect(favoriteBO);
           return true;
       }

    }





}
