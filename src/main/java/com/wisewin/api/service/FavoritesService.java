package com.wisewin.api.service;

import com.wisewin.api.dao.FavoritesDAO;
import com.wisewin.api.entity.bo.FavoritesResultBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FavoritesService {
    @Resource
    private FavoritesDAO favoritesDAO;


    public List<FavoritesResultBO> selectAll(Map<String,Object> map){


        return favoritesDAO.selectFavorites( map);

    }
}
