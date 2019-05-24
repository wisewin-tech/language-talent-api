package com.wisewin.api.web.controller;

import com.wisewin.api.common.constants.GiftConstants;
import com.wisewin.api.entity.bo.GiftBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.GiftParam;
import com.wisewin.api.service.GiftService;
import com.wisewin.api.service.UserService;
import com.wisewin.api.util.JsonUtils;
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

@Controller
@RequestMapping("/Gift")
public class GiftController extends BaseCotroller{


    @Resource
    private GiftService giftService;
    //用户
    @Resource
    private UserService userService;

    @RequestMapping("/queryGift")
    public void queryGift(HttpServletRequest request, HttpServletResponse response, GiftParam param){

        //获取当前用户
        UserBO loginUser = super.getLoginUser(request);
        Integer id = loginUser.getId();

        if (param.getExchangeyard().equals("") || id==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000029"));
            super.safeJsonPrint(response, json);
            return;
        }

        Map<String,Object> resulmap=new HashMap<String, Object>();

        //根据礼品兑换码查找
        GiftBO gift=giftService.getqueryGift(param.getExchangeyard());
        //根据用户id查找
        UserBO userBO=userService.selectById(id);

        //时间
        Date date1=new Date();

        //判断是否为空
        if (gift==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000029"));
            super.safeJsonPrint(response, json);
            return;

        }
        //判断礼品卡码是否使用
        if (gift.getStatus().equals(GiftConstants.NOT)){
            //判断使用时间
            //系统时间大于当前时间就等于1
            if(date1.compareTo(gift.getStarttime())==-1 ){
                resulmap.put("status",gift.getStatus());
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resulmap));
                super.safeJsonPrint(response, json);
                return;
            }
            //判断结束效期
            if (date1.compareTo(gift.getFinishtime())==1){
                resulmap.put("status",gift.getStatus());
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resulmap));
                super.safeJsonPrint(response, json);
                return;
            }
            //上面判断都通过了，然后在到礼品兑换表里添加兑换的记录
            boolean addGiftrecordjoin=giftService.getaddGiftrecord(gift.getId(),id,gift.getValue());
            if (addGiftrecordjoin){
                //修改礼品卡状态
                boolean updateGiftjsin=giftService.getupdateGift(gift.getId(),GiftConstants.USE);
                if (updateGiftjsin){
                    //修改用户咖豆值
                    Integer updateUserGiftjion=giftService.getupdateUserGift(id,userBO.getCurrency()+gift.getValue());
                    if (updateGiftjsin){
                        resulmap.put("state","使用成功");
                        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resulmap));
                        super.safeJsonPrint(response, json);
                        return;
                    }
                }

            }

        }
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000028"));
        super.safeJsonPrint(response, json);
        return;
    }
}
