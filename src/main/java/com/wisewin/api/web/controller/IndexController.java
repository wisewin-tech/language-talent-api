package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.*;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.DateUtils;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.TimeUtil;
import com.wisewin.api.util.date.DateUtil;
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
    private UserService userService;
    @Resource
    private SpecialClassService specialClassService;
    @Resource
    private SignService signService;
    @Resource
    LogService logService;
    /**
     * 首页展示
     * @param request
     * @param response
     */
    @RequestMapping("/showIndex")
    public void showIndex(HttpServletRequest request, HttpServletResponse response) {
        logService.startController(null, request);
        UserBO userBO = super.getLoginUser(request);
        if (userBO == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            logService.end("/index/showIndex", result);
            super.safeJsonPrint(response, result);
            return;
        }
            Integer useId = userBO.getId();
            logService.call("languageService.selectEnsignImage");
            List<LanguageBO> ensignImage = languageService.selectEnsignImage();
            logService.result(ensignImage);
            logService.call("languageService.getFlashSales");
            List<FlashSalesResultBO> flashSales = languageService.getFlashSales();
            logService.result(flashSales);

            for (FlashSalesResultBO flashSalesResultBO : flashSales) {
                Long discountTimeRemaining = DateUtils.parseDate(flashSalesResultBO.getDiscountEndTime(), "yyyy-MM-dd HH:mm:ss").getTime() - new Date().getTime();
                flashSalesResultBO.setDiscountTimeRemaining(discountTimeRemaining.toString());
            }
            logService.call("bannerService.getBanner");
            List<BannerBO> banner = bannerService.getBanner();
            logService.result(banner);
            //上次签到日期
            logService.call("userService.selectById",useId);
            UserBO userBO1 = userService.selectById(useId);
            logService.result(userBO1);

            Integer weekContinuousSigndays ;

            weekContinuousSigndays = userService.getWeekContinuousSign(useId);
            logService.result(weekContinuousSigndays);

            TodaySignOrNot todaySignOrNot = new TodaySignOrNot();
            int sign = signService.isSign(useId);

            int week = DateUtil.getWeek();
            if(weekContinuousSigndays==null ){ //重来没有签过
                todaySignOrNot.setTodaySignOrNot("no");
                weekContinuousSigndays = 0;
            }else if(sign>0){  //今天签了
                todaySignOrNot.setTodaySignOrNot("yes");
                if(week==1){
                       //今天是周一   返回签了一次
                    weekContinuousSigndays = 1;
                }
            }else {  //今天没签
               todaySignOrNot.setTodaySignOrNot("no");
               int let = signService.isletSign(useId);
               if(week==1 || let<1){ //今天是周一 或者昨天没有签到
                    weekContinuousSigndays = 0;  //返回签了0天
                }
            }
            logService.call("specialClassService.selectSpecialClassBO");
            List<SpecialClassBO> specialClassBOS = specialClassService.selectSpecialClassBO();
            logService.result(specialClassBOS);
            //signService.selectMon()
            Map map = new HashMap();
            //将对象封装到map中
            map.put("Banner", banner);
            map.put("todaySignOrNot", todaySignOrNot);
            map.put("weekContinuousSigndays", weekContinuousSigndays);
            map.put("EnsignImage", ensignImage);
            map.put("FlashSales", flashSales);
            map.put("specialClassBOS", specialClassBOS);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            logService.end("/index/showIndex", result);
            super.safeJsonPrint(response, result);
        }

}
