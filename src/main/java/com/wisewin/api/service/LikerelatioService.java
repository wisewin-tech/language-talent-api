package com.wisewin.api.service;


import com.wisewin.api.dao.LikerelationDAO;
import com.wisewin.api.entity.bo.LikerelationBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Service("/LikerelatioService")
public class LikerelatioService {

    @Resource
    private LikerelationDAO likerelationDAO;

    /**
     * 根据发现id和用户id来查找
     * Integer id;//喜欢id
     * Integer userId; //用户id
     * Integer dcId; //发现id
     */
    public LikerelationBO getfindLikerelation(Integer userId,Integer dcId){
        LikerelationBO likerelationBO=new LikerelationBO(userId,dcId);
        return  likerelationDAO.findLikerelation(likerelationBO);
    }

    /**
     * 添加喜欢用户与文章关系
     * Integer userId; //用户id
     * Integer dcId; //发现id
     */
    public boolean getaddLikerelation(Integer userId,Integer dcId){
        LikerelationBO likerelationBO=new LikerelationBO(userId,dcId);
        return  likerelationDAO.addLikerelation(likerelationBO)>0;
    }

    /**
     * 删除用户喜欢文章
     */
    public boolean getdeleteLikerelation(Integer id){
        return  likerelationDAO.deleteLikerelation(id)>0;
    }

    /**
     * 查找id
     */
    public LikerelationBO getfindLikerelationjson(Integer userId,Integer dcId){
        LikerelationBO likerelationBO=new LikerelationBO(userId,dcId);
        return  likerelationDAO.findLikerelationjson(likerelationBO);
    }
}
