package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.SignService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.RequestUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Shibo Sun
 *         主机controller
 */
@Controller
@RequestMapping("/sign")
public class SignController extends BaseCotroller {

    static final Logger log = LoggerFactory.getLogger(SignController.class);

    @Resource
    private SignService signService;

    /**
     *只有在用户登录的情况下,才能获取到用户ID
     * 进行搜索,否则参数异常
     * @param response
     * @param request
     */
    @RequestMapping("/selectSign")
    public void selectSign(HttpServletResponse response, HttpServletRequest request)  {
        log.info("start==================================================com.wisewin.api.web.controller.SignController.selectSign=============================================");
        log.info("请求ip{}", RequestUtils.getIpAddress(request));
        UserBO userBO = super.getLoginUser(request);
        log.info("userBo{}",userBO);
        if (userBO==null){
            log.info("userBo==null,return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        Integer userId=userBO.getId();
        if (userId==null){
            log.info("userId==nullreturn");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        log.info("调用com.wisewin.api.service.SignService.selectMon");
        Map<String,Object> map=signService.selectMon(userId);
        log.info("com.wisewin.api.service.SignService.selectMon返回{}",map);


        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        log.info("return{}",json);
        log.info("end================================================com.wisewin.api.web.controller.SignController.selectSign=======================================================");
        super.safeJsonPrint(response, json);


    }
    /**
     *用户签到,添加一条数据,签到日期为当前日期
     * 连续签到,查看上次签到日期和今天
     * 累计签到为查询到的最新数据对象中的属性
     * @param response
     * @param request
     */
    @RequestMapping("/signIn")
    public void signIn(HttpServletResponse response, HttpServletRequest request)  {
        log.info("start=================================com.wisewin.api.web.controller.SignController.signIn===============================");
        log.info("请求ip{}",RequestUtils.getIpAddress(request));
        Integer userId=super.getId(request);
        if (userId==null){
            log.info("userId==null,return ");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
            //如果今天第一次签到
        if ( signService.signIn(userId)){
            log.info("如果今天第一次签到");
            log.info("进入signService.signIn(userId)");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("恭喜你！签到成功！"));
            log.info("return{}",json);
            log.info("end=================================com.wisewin.api.web.controller.SignController.signIn===================================");
            super.safeJsonPrint(response, json);
        }else{
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000013"));
            log.info("return{}",json);
            log.info("end=================================com.wisewin.api.web.controller.SignController.signIn===================================");
            super.safeJsonPrint(response, json);
        }


    }

    /**
     * 获取keyvalue
     * */
    @RequestMapping("/getKeyValue")
    public void getKeyValue(String key,HttpServletResponse response, HttpServletRequest request){
        if (key==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        Object value = signService.getKeyValue(key);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(value));
        super.safeJsonPrint(response, json);

    }





}
