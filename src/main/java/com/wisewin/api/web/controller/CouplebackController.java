package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.CouplebackBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.CouplebackParam;
import com.wisewin.api.service.CouplebackService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * wy log
 * */
@Controller
@RequestMapping("/Coupleback")
public class CouplebackController  extends BaseCotroller {

    @Resource
    private CouplebackService couplebackService;

    @Resource
    LogService logService;
    /**
     * 添加反馈信息
     *  Integer userid; //用户id
     * String content; //反馈内容
     */
    @RequestMapping("/addCpupleback")
    public void addCpupleback(HttpServletRequest request, HttpServletResponse response, CouplebackParam param){
        //获取当前用户
        UserBO loginUser = super.getLoginUser(request);
        logService.startController(loginUser,request,param.toString());
        if (loginUser==null || StringUtils.isEmpty(param.getContent())){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/Coupleback/addCpupleback",json);
            return;
        }
        Integer id = loginUser.getId();

        //进行添加反馈内容
        logService.call("couplebackService.getaddCpupleback",id,param.getContent());
        boolean addCpuplebackjson=couplebackService.getaddCpupleback(id,param.getContent());
        logService.result(addCpuplebackjson);
        if (addCpuplebackjson){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("反馈成功"));
            super.safeJsonPrint(response, json);
            logService.end("/Coupleback/addCpupleback",json);
            return;
        }

        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response, json);
        logService.end("/Coupleback/addCpupleback",json);
        return;
    }
}
