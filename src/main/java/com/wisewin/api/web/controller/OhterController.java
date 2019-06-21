package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.ClauseBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.ClauseService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/other")
public class OhterController extends BaseCotroller {
    @Resource
    private ClauseService  clauseService;
    static final Logger log = LoggerFactory.getLogger(OhterController.class);


    @RequestMapping("/queryConfig")
    public void queryConfig(HttpServletRequest request,HttpServletResponse response,String config) {
        if(StringUtils.isEmpty(config)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        ClauseBO clauseBO = clauseService.selectClauseBOByClassify(config);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(clauseBO));
        super.safeJsonPrint(response, json);
        return;
    }







}
