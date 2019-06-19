package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.*;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
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
@RequestMapping("/language")
public class LanguageController extends BaseCotroller{

    @Resource
    private LanguageService languageService;
    @Resource
    private CourseService courseService;
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;
    @Resource
    LogService logService;

    /**
     * 语言详情
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/languageDetails")
    public void languageDetails(Integer id,HttpServletRequest request, HttpServletResponse response){
        logService.startController(null,request,id);
        if (id==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            logService.end("/language/languageDetails",result);
            return;
        }
        //获取登录用户信息
        UserBO userBO = super.getLoginUser(request);
        //获取不到用户则提示登录过期重新登录
        if (userBO==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, result);
            return;
        }
        Integer userId = userBO.getId();

        logService.call("languageService.languageDetails",id);
        LanguageDetailsResultBO languageBO = languageService.languageDetails(id);
        if (languageBO==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000040"));
            logService.end("/language/languageDetails",result);
            super.safeJsonPrint(response, result);
            return;
        }
        Date discountStartTime = languageBO.getDiscountStartTime();
        Date discountEndTime = languageBO.getDiscountEndTime();
        if (discountStartTime!=null&&discountEndTime!=null) {
            if (!DateUtil.belongCalendar(new Date(), discountStartTime, discountEndTime)) {
                languageBO.setLanguageDiscountPrice(0);
            }
        }
        logService.result(languageBO);
        logService.call("languageService.languageDetailsCourse",id);
        List<LanguageDetailsCourseResultBO> languageDetailsCourseResultBOS = languageService.languageDetailsCourse(id);
        for (LanguageDetailsCourseResultBO courseResultBO:languageDetailsCourseResultBOS){

                Date courseDiscountStartTime = courseResultBO.getDiscountStartTime();
                Date courseDiscountEndTime = courseResultBO.getDiscountEndTime();
            if (courseDiscountStartTime!=null&&courseDiscountEndTime!=null) {
                if (!DateUtil.belongCalendar(new Date(), courseDiscountStartTime, courseDiscountEndTime)) {
                    courseResultBO.setCourseDiscountPrice(0);
                }
            }
        }
        logService.result(languageDetailsCourseResultBOS);
        logService.call("courseService.getCourseIdById",id);
        List<CourseBO> courseBOS = courseService.getCourseIdById(id);
        logService.result(courseBOS);
            for (CourseBO courseBO : courseBOS) {
                Integer courseId = courseBO.getCourseId();
                Integer count = orderService.getStatusByCourseId(userId, courseId);
                if (count > 0) {
                    languageBO.setBuyOrNot("yes");
                }else if (count<=0){
                    languageBO.setBuyOrNot("no");
                }
            }
            if (courseBOS.size()==0&&languageBO!=null){
                languageBO.setBuyOrNot("no");
            }
        Map resultmap = new HashMap();
        resultmap.put("language",languageBO);
        resultmap.put("course",languageDetailsCourseResultBOS);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultmap));
        logService.end("/language/languageDetails",result);
        super.safeJsonPrint(response, result);
    }

    /**
     * 我学习的语言
     * @param request
     * @param response
     */
    @RequestMapping("/languageList")
    public void  languageList(HttpServletRequest request,HttpServletResponse response){
        UserBO userBO = super.getLoginUser(request);
        logService.startController(userBO,request);
        if (userBO==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, result);
            logService.end("/language/languageList",result);
            return;
        }
        Integer userId = userBO.getId();
        logService.call("languageService.myStudyLanguage",userId);
        List<MyStudyLanguageBO> languageBO = languageService.myStudyLanguage(userId);
        logService.result(languageBO);
        logService.call("languageService.languageList");
        List<MyStudyLanguageBO> languageBO1 = languageService.languageList();
        logService.result(languageBO1);
        Map resultMap = new HashMap();
        resultMap.put("myStudyLanguage",languageBO);
        resultMap.put("languageList",languageBO1);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
        logService.end("/language/languageList",result);
        super.safeJsonPrint(response, result);

    }

    /**
     * 修改正在学习的语言id
     * @param studyingLanguageId
     * @param request
     * @param response
     */
    @RequestMapping("/updateStudyingLanguage")
    public void updateStudyingLanguage(Integer studyingLanguageId,HttpServletRequest request,HttpServletResponse response){
        UserBO userBO = super.getLoginUser(request);
        logService.startController(userBO,request,studyingLanguageId);
        if (userBO==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, result);
            logService.end("/language/updateStudyingLanguage",result);
            return;
        }
        if (studyingLanguageId==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            logService.end("/language/updateStudyingLanguage",result);
            return;
        }

        Integer id = userBO.getId();
        logService.call("userService.userUpdate",id,studyingLanguageId);
        userService.userUpdate(id,studyingLanguageId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改我正在学习的语言成功！"));
        logService.end("/language/updateStudyingLanguage",result);
        super.safeJsonPrint(response, result);

    }
}
