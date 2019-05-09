package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.SpecialClassBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.SpecialClassService;
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

/**
 * 专题分类
 */
@Controller
@RequestMapping("/SpecialClass")
public class SpecialClassController extends BaseCotroller {

    @Resource
    SpecialClassService specialClassService;

    /**
     * 展示 专题分类
     * */
    @RequestMapping("/selectSpecialClassBO")
    public void selectSpecialClassBO(HttpServletRequest request, HttpServletResponse response){
        List<SpecialClassBO> specialClassBOList=specialClassService.selectSpecialClassBO();
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(specialClassBOList));
        super.safeJsonPrint(response,json);
    }




}
