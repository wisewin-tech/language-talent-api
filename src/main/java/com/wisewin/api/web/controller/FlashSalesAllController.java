package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.FlashSalesResultBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.pop.SystemConfig;
import com.wisewin.api.service.CourseService;
import com.wisewin.api.service.LanguageService;
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
import java.util.List;
/**
 * wy log
 * */
@Controller
@RequestMapping("/flashSales")
public class FlashSalesAllController extends BaseCotroller{
    @Resource
    private LanguageService languageService;
    @Resource
    private CourseService courseService;
    @Resource
    LogService logService;
    @RequestMapping("/getAllFlashSales")
    public void getAllFlashSales(HttpServletRequest request, HttpServletResponse response){
        logService.startController(null,request);
        logService.call("languageService.getAllFlashSales");
        List<FlashSalesResultBO> flashSalesResultBOS = languageService.getAllFlashSales();
        logService.result(flashSalesResultBOS);
        for (FlashSalesResultBO flashSalesResultBO: flashSalesResultBOS){
            Long discountTimeRemaining = DateUtils.parseDate(flashSalesResultBO.getDiscountEndTime(),"yyyy-MM-dd HH:mm:ss").getTime()- new Date().getTime();
            flashSalesResultBO.setDiscountTimeRemaining(discountTimeRemaining.toString());
           Long discountStartTime = DateUtils.parseDate(flashSalesResultBO.getDiscountStartTime(),"yyyy-MM-dd HH:mm:ss").getTime();
           flashSalesResultBO.setDiscountStartTime(discountStartTime.toString());
           logService.call("courseService.getCourseIdById");
           List<CourseBO> courseBOS = courseService.getCourseIdById(flashSalesResultBO.getLanguageId());
           logService.result(courseBOS);
            if (courseBOS.size()==0){
                flashSalesResultBO.setCertificateOrNot("no");
            }
            for (CourseBO courseBO:courseBOS){
                if ("yes".equals(courseBO.getCertificateOrNot())){
                    flashSalesResultBO.setCertificateOrNot("yes");
                }
            }

        }


        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(flashSalesResultBOS));
        logService.end("/flashSales/getAllFlashSales");
        super.safeJsonPrint(response, result);
    }

}
