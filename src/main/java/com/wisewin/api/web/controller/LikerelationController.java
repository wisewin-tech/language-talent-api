package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.LikerelationBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.LikerelationParam;
import com.wisewin.api.service.DiscoverService;
import com.wisewin.api.service.LikerelatioService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Likerelation")
public class LikerelationController extends BaseCotroller{

    @Resource
    private LikerelatioService likerelatioService;

    @Resource
    private DiscoverService discoverService;

    /**
     * 根据发现id和用户id来查找
     * Integer userId; //用户id
     * Integer dcId; //发现id
     */
    @RequestMapping("/findLikerelation")
    public void findLikerelation(HttpServletRequest request, HttpServletResponse response, LikerelationParam param){

     if (param.getUserId()==null && param.getDcId()==null){
         String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
         super.safeJsonPrint(response, json);
         return;
     }

     //通过用户id和文章id查询出有数据说明用户已喜欢，如果查出为null说明用户没有喜欢
        LikerelationBO likerelationjson=likerelatioService.getfindLikerelation(param.getUserId(),param.getDcId());
        if (likerelationjson!=null){
            //查找喜欢的值
            DiscoverBO findDiscoverlikenumjson=discoverService.getfindDiscoverlikenum(param.getDcId());
            if (findDiscoverlikenumjson.getLikenum()!=null){
                //根据用户id和发现id查找出喜欢关系表里的id删除
                boolean  getdeleteLikerelationjson=likerelatioService.getdeleteLikerelation(likerelationjson.getId());
                if (getdeleteLikerelationjson){
                    //这个值是从刚刚查询发现表里取出来的数据
                    Integer likenum=findDiscoverlikenumjson.getLikenum()-1;
                    //修改喜欢值
                    boolean  updatelikenumDiscoverjson=discoverService.getupdatelikenumDiscover(param.getDcId(),likenum);
                    if (updatelikenumDiscoverjson){
                        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("取消喜欢"));
                        super.safeJsonPrint(response, json);
                        return;

                    }
                }
            }
        }
            //查找喜欢的值
            DiscoverBO findDiscoverlikenumjson=discoverService.getfindDiscoverlikenum(param.getDcId());
            if (findDiscoverlikenumjson.getLikenum()!=null){
                //添加用户id发现id关系
                boolean getaddLikerelationjson=likerelatioService.getaddLikerelation(param.getUserId(),param.getDcId());
                if (getaddLikerelationjson){
                    //这个值是从刚刚查询发现表里取出来的数据
                    Integer likenum=findDiscoverlikenumjson.getLikenum()+1;
                    //修改喜欢值
                    boolean  updatelikenumDiscoverjson=discoverService.getupdatelikenumDiscover(param.getDcId(),likenum);
                    if (updatelikenumDiscoverjson){
                        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("喜欢成功"));
                        super.safeJsonPrint(response, json);
                        return;

                    }
                }


        }
    }
}
