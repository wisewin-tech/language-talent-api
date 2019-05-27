package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.OrderService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
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
@RequestMapping("/order")
public class OrderController extends BaseCotroller {
    @Resource
    private OrderService orderService;

    /**
     * 查询交易记录(包括购买/充值记录)
     * @param pageNo
     * @param pageSize
     * @param response
     * @param request
     */
    @RequestMapping("/selectAll")
    public void selectSign(Integer pageNo, Integer pageSize, HttpServletResponse response, HttpServletRequest request) {
        //判断用户id是否为空,即:用户是否登录
        Integer userId = super.getId(request);
        if (userId == null ) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        //封装limit条件,pageNo改为页数
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if (queryInfo != null) {
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageOffset", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }
        //把userId 用户id,status 状态:支出/获取 放入map集合中
        condition.put("userId", userId);
        //吧封装好的条件传给service
        List<OrderBO> list= orderService.selectAll(condition);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, json);

    }
    @RequestMapping("/selectDetails")
    public void selectDetails(String id,HttpServletResponse response, HttpServletRequest request) {
        //判断用户id是否为空,即:用户是否登录
        Integer userId = super.getId(request);
        if (StringUtils.isEmpty(id)) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(userId == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        OrderBO orderBO=orderService.selectDetails(Integer.parseInt(id),userId);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO));
        super.safeJsonPrint(response, json);

    }
}
