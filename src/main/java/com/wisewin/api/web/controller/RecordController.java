package com.wisewin.api.web.controller;
import com.wisewin.api.entity.bo.RecordBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.RecordService;
import com.wisewin.api.service.SignService;
import com.wisewin.api.service.UserService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseCotroller{
    @Resource
    private RecordService recordService;
    @Resource
    private UserService userService;
    /**
     * 查询积分支出/获取记录
     * @param pageNo    页数
     * @param pageSize  每页条数
     * @param status    状态:支出/获取
     * @param response
     * @param request
     */
    @RequestMapping("/selectIntegral")
    public void selectSign(Integer pageNo, Integer pageSize,HttpServletResponse response, HttpServletRequest request,String status)  {
        //判断用户id是否为空,即:用户是否登录
        Integer userId=super.getId(request);
        if (userId==null||status==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        //封装limit条件,pageNo改为页数
        QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if(queryInfo != null){
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageOffset", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }
            //把userId 用户id,status 状态:支出/获取 放入map集合中
            condition.put("userId",userId);
            condition.put("status",status);

        //把带有条件的查询结果集放入map中
        List<RecordBO> recordBOList= recordService.selectIntegralInt(condition);
        Integer count=recordService.selectUserRecord(userId);
        Map<String, Object> map=new HashMap<String, Object>();
        //recordBOList的结果集
        map.put("recordBOList",recordBOList);
        //积分查询总条数
        map.put("count",count);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, json);
        return;


    }
    /**
     * 兑换咖币
     * @param response
     * @param request
     */
    @RequestMapping("/exchangeInfo")
    public void exchangeInfo(HttpServletResponse response, HttpServletRequest request)  {
        //判断用户id是否为空,即:用户是否登录
        Integer userId=super.getId(request);
        if (userId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
            UserBO userBO = userService.selectById(userId);
            //获取用户积分信息
            Integer integral = userBO.getIntegral();
            Map<String, Object> map = recordService.exchangeInformation();
            map.put("integral",integral);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            super.safeJsonPrint(response, json);
            return;


    }
    /**
     * 确认兑换
     * @param num   兑换的数量
     * @param response
     * @param request
     */
    @RequestMapping("/exchange")
    public void exchange(Integer num,HttpServletResponse response, HttpServletRequest request)  {
        //判断用户id是否为空,即:用户是否登录
        Integer userId=super.getId(request);
        if (userId==null||num==null||num<0){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if (recordService.exchange(userId,num)){
            UserBO userBO = userService.selectById(userId);
            //获取用户积分信息
            Integer integral = userBO.getIntegral();
            //获取用户咖豆信息
            Integer currency = userBO.getCurrency();
            Map<String, Object> map = recordService.exchangeInformation(num);
            map.put("integral",integral);
            map.put("currency",currency);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            super.safeJsonPrint(response, json);
        }else{
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000015"));
            super.safeJsonPrint(response, json);
            return;
        }

    }

}
