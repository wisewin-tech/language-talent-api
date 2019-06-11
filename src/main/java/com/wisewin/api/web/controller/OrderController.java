

package com.wisewin.api.web.controller;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.OrderService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseCotroller {

    static final Logger log = LoggerFactory.getLogger(OrderController.class);

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
        log.info("start==================================com.wisewin.api.web.controller.OrderController.selectSign===========================================");
        log.info("请求ip{}", RequestUtils.getIpAddress(request));
        log.info("参数pageNo{}",pageNo);
        log.info("参数pageSize{}",pageSize);
        //判断用户id是否为空,即:用户是否登录
        UserBO user = super.getLoginUser(request);
        log.info("user:{}",user);
        if (user== null ) {
            log.info("user== null,retrun");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
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
        condition.put("userId", user.getId());
        //吧封装好的条件传给service
        List<OrderBO> list= orderService.selectAll(condition);
        System.err.println(list);
        if(list == null ){
            log.info("list<=0return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000039"));
            super.safeJsonPrint(response, json);
            return;
        }
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, json);
        log.info("return{}",json);
        log.info("end===================================com.wisewin.api.web.controller.OrderController.selectSign===========================");
        return;
    }


    @RequestMapping("/selectDetails")
    public void selectDetails(String id,HttpServletResponse response, HttpServletRequest request) {
        log.info("start=============================com.wisewin.api.web.controller.OrderController.selectDetails===========================");
        log.info("请求ip{}",RequestUtils.getIpAddress(request));
        log.info("参数id{}",id);
        //判断用户id是否为空,即:用户是否登录
        UserBO user = super.getLoginUser(request);
        if (StringUtils.isEmpty(id)) {
            log.info("StringUtils.isEmpty(id),return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(user == null){
            log.info("user == null,return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        OrderBO orderBO=orderService.selectDetails(Integer.parseInt(id),user.getId());
        if(orderBO == null){
            log.info("orderBO == null,return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000038"));
            super.safeJsonPrint(response, json);
            return;
        }
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO));
        super.safeJsonPrint(response, json);
        log.info("return{}",json);
        log.info("end===================================com.wisewin.api.web.controller.OrderController.selectDetails===========================");
        return;
    }
}

