package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.CourseService;
import com.wisewin.api.service.OrderService;
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
@RequestMapping("/course")
public class CourseController extends BaseCotroller {
    @Resource
    private CourseService courseService;
    @Resource
    private OrderService orderService;

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
            return;
        }
        UserBO userBO = super.getLoginUser(request);
        Integer userId = userBO.getId();
        //查看课程详情
        CourseDetailsResultBO courseDetailsResultBO = courseService.courseDetailsCourse(id);
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
        //验证参数
        if (StringUtils.isEmpty(languageName)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        List<CourseDetailsResultBO> courseBOList = courseService.courseSearch(languageName);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(courseBOList));
        super.safeJsonPrint(response, json);
    }

    /**
     * 热门课程
     * @param request
     * @param response
     */
    @RequestMapping("/getHotCourse")
    public void getHotCourse(HttpServletRequest request,HttpServletResponse response){
        List<HotCourseResultBO> hotCourseResultBOS = courseService.hotCourse();
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(hotCourseResultBOS));
        super.safeJsonPrint(response, json);
    }
}
