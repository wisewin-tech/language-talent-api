package com.wisewin.api.web.controller;

import com.wisewin.api.common.constants.GiftConstants;
import com.wisewin.api.entity.bo.GiftBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.GiftParam;
import com.wisewin.api.service.GiftService;
import com.wisewin.api.service.UserService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * wy log
 * */
@Controller
@RequestMapping("/Gift")
public class GiftController extends BaseCotroller{

    @Resource
    LogService logService;

    @Resource
    private GiftService giftService;
    //用户
    @Resource
    private UserService userService;

    @RequestMapping("/queryGift")
    public void queryGift(HttpServletRequest request, HttpServletResponse response, GiftParam param){
        //获取当前用户
        UserBO loginUser =  super.getLoginUser(request);

        logService.startController(loginUser,request,param);
        if (loginUser==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            logService.end("/Gift/queryGift",json);
            return;
        }

        if (StringUtils.isEmpty(param.getExchangeyard())){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000029"));
            super.safeJsonPrint(response, json);
            logService.end("/Gift/queryGift",json);
            return;
        }

        //根据礼品兑换码查找
        logService.call("giftService.getqueryGift",param.getExchangeyard());
        GiftBO gift=giftService.getqueryGift(param.getExchangeyard());
        if(gift==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000029"));
            super.safeJsonPrint(response, json);
            logService.end("/Gift/queryGift ",json);
            return;
        }
        //更新用户对象
        logService.call("giftService.getaddGiftrecord",gift.getId(),loginUser.getId(),gift.getValue());
        loginUser= userService.selectById(loginUser.getId());
        logService.result(loginUser);
        //修改礼品卡状态
        logService.call("giftService.getupdateGift",gift.getId(),GiftConstants.USE);
        giftService.getupdateGift(gift.getId(),GiftConstants.USE);

        //添加礼品卡记录
        logService.call("giftService.getaddGiftrecord",gift.getId(),loginUser.getId(),gift.getValue());
        giftService.getaddGiftrecord(gift.getId(),loginUser.getId(),gift.getValue());


        logService.call("giftService.getupdateUserGift",loginUser,gift.getValue());
        Integer updateUserGiftjion=giftService.getupdateUserGift(loginUser,gift.getValue());
        logService.result(updateUserGiftjion);

        Map<String,String>  resultMap=new HashMap<String, String>();
        resultMap.put("state","使用成功");
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
        super.safeJsonPrint(response, json);
        logService.end("/Gift/queryGift",json);
        return;
    }
}
