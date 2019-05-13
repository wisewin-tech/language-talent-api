package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.BannerBO;
import com.wisewin.api.entity.bo.FlashSalesResultBO;
import com.wisewin.api.entity.bo.LanguageBO;
import com.wisewin.api.entity.bo.SpecialClassBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.BannerService;
import com.wisewin.api.service.LanguageService;
import com.wisewin.api.service.SignService;
import com.wisewin.api.service.SpecialClassService;
import com.wisewin.api.util.DateUtils;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseCotroller {
    @Resource
    private LanguageService languageService;
    @Resource
    private BannerService bannerService;
    @Resource
    private SignService signService;
    @Resource
    private SpecialClassService specialClassService;

    @RequestMapping("/showIndex")
    public void showIndex(HttpServletRequest request, HttpServletResponse response){
        Integer useId = super.getId(request);
        List<LanguageBO> ensignImage = languageService.selectEnsignImage();
        List<FlashSalesResultBO> flashSales = languageService.getFlashSales();
        for (FlashSalesResultBO flashSalesResultBO: flashSales){
            Long discountTimeRemaining =DateUtils.parseDate(flashSalesResultBO.getDiscountEndTime(),"yyyy-MM-dd HH:mm:ss").getTime()- new Date().getTime();
            flashSalesResultBO.setDiscountTimeRemaining(discountTimeRemaining);
        }
        List<BannerBO> banner = bannerService.getBanner();
        Integer ContinuousSigndays = signService.getContinuousSign(useId);
        List<SpecialClassBO> specialClassBOS = specialClassService.selectSpecialClassBO();
        //signService.selectMon()
        Map map = new HashMap();
        //将对象封装到map中
        map.put("Banner",banner);
        map.put("ContinuousSigndays",ContinuousSigndays);
        map.put("EnsignImage",ensignImage);
        map.put("FlashSales",flashSales);
        map.put("specialClassBOS",specialClassBOS);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, result);
    }

}
