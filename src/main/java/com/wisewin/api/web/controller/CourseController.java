package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.CourseService;
import com.wisewin.api.service.OrderService;
import com.wisewin.api.service.base.LogService;
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
/**
 * wy log
 * */
@Controller
@RequestMapping("/course")
public class CourseController extends BaseCotroller {
    @Resource
    private CourseService courseService;
    @Resource
    private OrderService orderService;

    @Resource
    LogService logService;
    /**
     * 课程详情
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/courseDetails")
    public void courseDetails(Integer id,HttpServletRequest request, HttpServletResponse response) {
        if (id==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            logService.end("/course/courseDetails",result);
            return;
        }
        UserBO userBO = super.getLoginUser(request);
        logService.startController(userBO,request,id);
        Integer userId = userBO.getId();
        //查看课程详情
        logService.call("courseService.courseDetailsCourse",id);
        CourseDetailsResultBO courseDetailsResultBO = courseService.courseDetailsCourse(id);
        logService.result(courseDetailsResultBO);
        List<CourseDetailsLevelResultBO> levelBOS= courseService.courseDetailsLevel(id);
        Integer count = orderService.getStatusByCourseId(userId,id);
        if (courseDetailsResultBO!=null) {
            if (count>0) {
                courseDetailsResultBO.setBuyOrNot("yes");
            }else {
                courseDetailsResultBO.setBuyOrNot("no");
            }
        }

        Map map = new HashMap();
        map.put("courseDetailsResultBO",courseDetailsResultBO);
        map.put("CourseDetailsLevelResultBO",levelBOS);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        logService.end("/course/courseDetails",result);
        super.safeJsonPrint(response, result);

    }

    /**
     * 课程搜索
     * @param languageName
     * @param request
     * @param response
     */
    @RequestMapping("/courseSearch")
    public void courseSearch(String languageName, HttpServletRequest request, HttpServletResponse response){
        logService.startController(null,request,languageName);
        //验证参数
        if (StringUtils.isEmpty(languageName)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            logService.end("/course/courseSearch",json);
            super.safeJsonPrint(response, json);
        }
        logService.call("courseService.courseSearch",languageName);
        List<CourseDetailsResultBO> courseBOList = courseService.courseSearch(languageName);
        logService.result(courseBOList);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(courseBOList));
        logService.end("/course/courseSearch",json);
        super.safeJsonPrint(response, json);
    }

    /**
     * 热门课程
     * @param request
     * @param response
     */
    @RequestMapping("/getHotCourse")
    public void getHotCourse(HttpServletRequest request,HttpServletResponse response){
        logService.startController(null,request);
        logService.call("courseService.hotCourse");
        List<HotCourseResultBO> hotCourseResultBOS = courseService.hotCourse();
        logService.result(hotCourseResultBOS);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(hotCourseResultBOS));
        super.safeJsonPrint(response, json);
        logService.end("/course/getHotCourse",json);
    }
}
