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
     * Integer userId; //用户id
     * Integer dcId; //发现id
     */
    public boolean getfindLikerelation(Integer userId,Integer dcId){

        return  likerelationDAO.findLikerelation(userId,dcId)>0;
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
    public boolean getdeleteLikerelation(Integer userId,Integer dcId){

        return  likerelationDAO.deleteLikerelation(userId,dcId)>0;
    }

    /**
     * 查找id
     */
    public LikerelationBO getfindLikerelationjson(Integer userId,Integer dcId){
        LikerelationBO likerelationBO=new LikerelationBO(userId,dcId);
        return  likerelationDAO.findLikerelationjson(likerelationBO);
    }


    /**
     * param dcId 文章id
     * return  喜欢总次数
     */
    public  int queryLikereCount(Integer dcId){
        return likerelationDAO.queryLikereCount(dcId);
    }
}
