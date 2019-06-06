package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.AboutUsBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.AboutUsService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 关于我们
 */
@Controller
@RequestMapping("/aboutUs")
public class AboutUsController extends BaseCotroller {

    @Resource
    private AboutUsService aboutUsService ;

    /**
     * 查询关于我们
     * @param request
     * @param response
     */
    @RequestMapping("/selectAboutUs")
    public void selectAboutUs(HttpServletRequest request,HttpServletResponse response) {
        //通过查询信息,返回aboutUs对象
        AboutUsBO aboutUs=aboutUsService.selectContent();
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(aboutUs));
        super.safeJsonPrint(response, json);
    }
}
