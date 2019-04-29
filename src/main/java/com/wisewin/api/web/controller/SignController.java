package com.wisewin.api.web.controller;

import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.SignService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
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
        Integer userId=super.getId(request);
        if (userId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        Map<String,Object> mapSign=signService.selectMon(userId);

        System.out.println("Map集合签到数据:"+mapSign);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapSign));
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
        Integer userId=super.getId(request);
        if (userId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if ( signService.signIn(userId)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("签到成功"));
            super.safeJsonPrint(response, json);
        }else{
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001","今天已经签到,无须重复"));
            super.safeJsonPrint(response, json);
        }


    }


}
