package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.LikerelationBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.LikerelationParam;
import com.wisewin.api.service.DiscoverService;
import com.wisewin.api.service.LikerelatioService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.RequestUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static final Logger log = LoggerFactory.getLogger(LikerelationController.class);

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
        log.info("start========================com.wisewin.api.web.controller.LikerelationController.findLikerelation===========================");
        log.info("请求ip{}", RequestUtils.getIpAddress(request));
        log.info("参数param{}",param);
        //获取当前用户id
        UserBO loginUser = super.getLoginUser(request);
        if(loginUser==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        if (StringUtils.isObjEmpty(param.getDcId())){
         log.info("id==null || StringUtils.isObjEmpty(param.getDcId()),return");
         String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
         super.safeJsonPrint(response, json);
         return;
        }
        Integer id = loginUser.getId();

        Map<String,Object> resultMap=new HashMap<String, Object>();
       //判断用户有没有喜欢过
        boolean likerelationjson=likerelatioService.getfindLikerelation(id,param.getDcId());
        if (likerelationjson){
            //减少喜欢总数 1
            discoverService.getupdatelikenumDiscover(param.getDcId(),"no");
            //删除喜欢关系
            likerelatioService.getdeleteLikerelation(loginUser.getId(),param.getDcId());
            resultMap.put("msg","取消喜欢成功");
            resultMap.put("type","0");
        }else{
            //添加喜欢
            discoverService.getupdatelikenumDiscover(param.getDcId(),"yes");
            //删除喜欢关系
            likerelatioService.getaddLikerelation(loginUser.getId(),param.getDcId());
            resultMap.put("msg","增加喜欢成功");
            resultMap.put("type","1");
        }
        //喜欢总数 discoverBO
        int likeCount = likerelatioService.queryLikereCount(param.getDcId());
        resultMap.put("likeCount",likeCount);

        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
        log.info("return{}",json);
        log.info("end========================com.wisewin.api.web.controller.LikerelationController.findLikerelation===========================");
        super.safeJsonPrint(response, json);
        return;
        }
}
