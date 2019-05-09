package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.SpecialBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.SpecialService;
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
 *
 */
@Controller
@RequestMapping("/Special")
public class SpecialController extends BaseCotroller {

    @Resource
    SpecialService specialService;

    /**
     * 按展示或者为展示也就是yes no 展示 专题分类
     * */
    @RequestMapping("selectSpecialBO")
    public void selectSpecialBO(HttpServletRequest request, HttpServletResponse response){
        List<SpecialBO> specialBOList=specialService.selectSpecialBO();
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(specialBOList));
        super.safeJsonPrint(response,json);
    }





}
