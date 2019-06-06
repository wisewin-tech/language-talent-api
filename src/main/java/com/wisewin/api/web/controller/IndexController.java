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
import com.wisewin.api.service.base.LogService;
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
/**
 * wy log
 * */
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
    @Resource
    LogService logService;
    /**
     * 首页展示
     * @param request
     * @param response
     */
    @RequestMapping("/showIndex")
    public void showIndex(HttpServletRequest request, HttpServletResponse response){
        logService.startController(null,request);
        Integer useId = super.getId(request);
        logService.call("languageService.selectEnsignImage");
        List<LanguageBO> ensignImage = languageService.selectEnsignImage();
        logService.result(ensignImage);
        logService.call("languageService.getFlashSales");
        List<FlashSalesResultBO> flashSales = languageService.getFlashSales();
        logService.result(flashSales);

        for (FlashSalesResultBO flashSalesResultBO: flashSales){
            Long discountTimeRemaining =DateUtils.parseDate(flashSalesResultBO.getDiscountEndTime(),"yyyy-MM-dd HH:mm:ss").getTime()- new Date().getTime();
            flashSalesResultBO.setDiscountTimeRemaining(discountTimeRemaining.toString());
        }
        logService.call("bannerService.getBanner");
        List<BannerBO> banner = bannerService.getBanner();
        logService.result(banner);
        logService.call("signService.getContinuousSign",useId);
        Integer ContinuousSigndays = signService.getContinuousSign(useId);
        logService.result(ContinuousSigndays);
        logService.call("specialClassService.selectSpecialClassBO");
        List<SpecialClassBO> specialClassBOS = specialClassService.selectSpecialClassBO();
        logService.result(specialClassBOS);
        //signService.selectMon()
        Map map = new HashMap();
        //将对象封装到map中
        map.put("Banner",banner);
        map.put("ContinuousSigndays",ContinuousSigndays);
        map.put("EnsignImage",ensignImage);
        map.put("FlashSales",flashSales);
        map.put("specialClassBOS",specialClassBOS);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        logService.end("/index/showIndex",result);
        super.safeJsonPrint(response, result);
    }

}
