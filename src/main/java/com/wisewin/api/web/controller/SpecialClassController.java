package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.SpecialClassBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.SpecialClassService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专题分类
 */
@Controller
@RequestMapping("/SpecialClass")
public class SpecialClassController extends BaseCotroller {
    static final Logger log = LoggerFactory.getLogger(SpecialClassController.class);

    @Resource
    SpecialClassService specialClassService;

    /**
     * 展示 专题分类
     * */
    @RequestMapping("/selectSpecialClassBO")
    public void selectSpecialClassBO(HttpServletRequest request, HttpServletResponse response){
        log.info("start=========================com.wisewin.api.web.controller.SpecialClassController.selectSpecialClassBO=================");
        log.info("请求ip{}", RequestUtils.getIpAddress(request));
        List<SpecialClassBO> specialClassBOList=specialClassService.selectSpecialClassBO();
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(specialClassBOList));
        log.info("return{}",json);
        log.info("end===========================com.wisewin.api.web.controller.SpecialClassController.selectSpecialClassBO");
        super.safeJsonPrint(response,json);
    }




}
