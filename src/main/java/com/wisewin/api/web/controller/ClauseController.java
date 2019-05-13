package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.ClauseBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.ClauseService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  条款
 */
@Controller
@RequestMapping("/Clause")
public class ClauseController extends BaseCotroller {

    @Resource
    ClauseService clauseService;

    /**
     * 查询一条条款
     * */
    @RequestMapping("/selectClauseBOByClassifv")
    public void selectClauseBOByClassifv(HttpServletRequest request, HttpServletResponse response,String classify){
        if(classify==null || classify.equals("")){
            String languagejson=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response,languagejson);
            return;
        }
        ClauseBO ClauseBO=clauseService.selectClauseBOByClassify(classify);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(ClauseBO));
        super.safeJsonPrint(response,json);
    }

}
