package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.LikerelationBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.LikerelationParam;
import com.wisewin.api.service.DiscoverService;
import com.wisewin.api.service.LikerelatioService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
        //获取当前用户id
        UserBO loginUser = super.getLoginUser(request);
        Integer id = loginUser.getId();
        if (id==null || StringUtils.isObjEmpty(param.getDcId())){
         String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
         super.safeJsonPrint(response, json);
         return;
      }
      //是否真实存在
            DiscoverBO discoverBO=discoverService.getfindDiscoverlikenum(param.getDcId());
        if (discoverBO==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        Map<String,Object> resultMap=new HashMap<String, Object>();
       //判断用户有没有喜欢过
        boolean likerelationjson=likerelatioService.getfindLikerelation(id,param.getDcId());
        if (likerelationjson){
            //减少喜欢总数 1
            discoverService.getupdatelikenumDiscover(param.getDcId(),discoverBO.getLikenum()-1);
            //删除喜欢关系
            likerelatioService.getdeleteLikerelation(loginUser.getId(),param.getDcId());
            resultMap.put("msg","取消喜欢成功");
        }else{
            //添加喜欢
            discoverService.getupdatelikenumDiscover(param.getDcId(),discoverBO.getLikenum()+1);
            //删除喜欢关系
            likerelatioService.getaddLikerelation(loginUser.getId(),param.getDcId());
            resultMap.put("msg","增加喜欢成功");
        }
        //喜欢总数 discoverBO
        int likeCount = likerelatioService.queryLikereCount(param.getDcId());
        resultMap.put("likeCount",likeCount);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
        super.safeJsonPrint(response, json);
        }
}
