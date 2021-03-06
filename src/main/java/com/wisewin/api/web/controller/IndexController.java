package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.*;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.DateUtils;
import com.wisewin.api.util.JsonUtils;
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
            //获取本周周一的日期
            String monday = DateUtil.getWeekStart(new Date());
            //获取本周周日的日期
            String sunday = DateUtil.getWeekEnd(new Date());
            Integer weekContinuousSigndays ;
            UserBO userBO2 = userService.selectById(useId);
            if (userBO2.getLastSign()==null){
                weekContinuousSigndays = 0;
            }else {
                boolean i = DateUtil.belongCalendar(userBO2.getLastSign(), DateUtil.getDate(monday), DateUtil.getDate(sunday));
                String yesterday = DateUtil.getYseterday();
                String lastSignDate = DateUtil.getDateStr(userBO2.getLastSign());
                if (!(i||yesterday.equals(lastSignDate))) {
                    weekContinuousSigndays = 0;
                } else {
                    logService.call("signService.getContinuousSign", useId);
                    weekContinuousSigndays = userService.getWeekContinuousSign(useId);
                    logService.result(weekContinuousSigndays);
                }
            }

            //查询签到表用户最新记录
            SignBO signBO = signService.selectNew(useId);
            TodaySignOrNot todaySignOrNot = new TodaySignOrNot();
            if (signBO==null){
                todaySignOrNot.setTodaySignOrNot("no");
            }else {
                if (signBO.getSignTime().equals(DateUtil.getDateStr(new Date()))) {
                    todaySignOrNot.setTodaySignOrNot("yes");
                } else {
                    todaySignOrNot.setTodaySignOrNot("no");
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
