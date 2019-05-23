package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.*;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
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

    /**
     * 语言详情
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/languageDetails")
    public void languageDetails(Integer id,HttpServletRequest request, HttpServletResponse response){
        if (id==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            return;
        }
        //获取登录用户信息
        UserBO userBO = super.getLoginUser(request);
        Integer userId = userBO.getId();


        LanguageDetailsResultBO languageBO = languageService.languageDetails(id);
        List<LanguageDetailsCourseResultBO> languageDetailsCourseResultBOS = languageService.languageDetailsCourse(id);

        List<CourseBO> courseBOS = courseService.getCourseIdById(id);

            for (CourseBO courseBO : courseBOS) {
                Integer courseId = courseBO.getCourseId();
                Integer count = orderService.getStatusByCourseId(userId, courseId);
                if (count > 0) {
                    languageBO.setBuyOrNot("yes");
                }
            }
            languageBO.setBuyOrNot("no");


        Map resultmap = new HashMap();
        resultmap.put("language",languageBO);
        resultmap.put("course",languageDetailsCourseResultBOS);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultmap));
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
        if (userBO==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, result);
            return;
        }
        Integer userId = userBO.getId();
        List<LanguageBO> languageBO = languageService.myStudyLanguage(userId);
        List<LanguageBO> languageBO1 = languageService.languageList();
        Map resultMap = new HashMap();
        resultMap.put("myStudyLanguage",languageBO);
        resultMap.put("languageList",languageBO1);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
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
        if (studyingLanguageId==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            return;
        }
        UserBO userBO = super.getLoginUser(request);
        if (userBO==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, result);
            return;
        }
        Integer id = userBO.getId();
        userService.userUpdate(id,studyingLanguageId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改我正在学习的语言成功！"));
        super.safeJsonPrint(response, result);

    }
}
