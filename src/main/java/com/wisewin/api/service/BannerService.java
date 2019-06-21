package com.wisewin.api.service;

import com.wisewin.api.dao.BannerDAO;
import com.wisewin.api.entity.bo.BannerBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("bannerService")
@Transactional
public class BannerService {
    @Resource
    private BannerDAO bannerDAO;


    /**
     * banner图展示
     * @return
     */
    public List<BannerBO> getBanner(){
        List<BannerBO> list=bannerDAO.getBanner();
        //如果想避免这个问题 那只能判断直接把下架的banner删了了
//        for (BannerBO bannerBO:list) {
//            String type =  bannerBO.getType();
//            if(type.equals("course")){
//
//            }else if(type.equals("language")){
//
//            }else if(type.equals("article")){
//
//            }else if(type.equals("topic")){
//
//            }else if(type.equals("activity")){
//
//            }else if(type.equals("video")){
//
//            }
//        }
        return list;
    }
}
