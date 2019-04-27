package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.BannerBO;
import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.LanguageBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.BannerService;
import com.wisewin.api.service.CourseService;
import com.wisewin.api.service.LanguageService;
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

@Controller
@RequestMapping("/index")
public class IndexController extends BaseCotroller {
    @Resource
    private LanguageService languageService;
    @Resource
    private CourseService courseService;
    @Resource
    private BannerService bannerService;
    @RequestMapping("/showIndex")
    public void showIndex(HttpServletRequest request, HttpServletResponse response){
        List<LanguageBO> ensignImage = languageService.selectEnsignImage();
        List<LanguageBO> flashSales = languageService.getFlashSales();
        List<CourseBO> hotCourse = courseService.getHotCourse();
        List<BannerBO> banner = bannerService.getBanner();
        Map map = new HashMap();
        map.put("EnsignImage",ensignImage);
        map.put("FlashSales",flashSales);
        map.put("HotCourse",hotCourse);
        map.put("Banner",banner);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, result);
    }

}