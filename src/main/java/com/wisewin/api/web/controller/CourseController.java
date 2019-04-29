package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.LanguageBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.CourseService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController extends BaseCotroller {
    @Resource
    private CourseService courseService;

    @RequestMapping("/courseDetails")
    public void courseDetails(CourseBO id,HttpServletRequest request, HttpServletResponse response) {
        //查看课程详情
        List<CourseBO> courseBOList = courseService.courseDetails(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(courseBOList));
        super.safeJsonPrint(response, result);

    }

    @RequestMapping("/courseSearch")
    public void courseSearch(String languageName, HttpServletRequest request, HttpServletResponse response){
        //验证参数
        if (StringUtils.isEmpty(languageName)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数不正确！"));
            super.safeJsonPrint(response, json);
        }
        List<CourseBO> courseBOList = courseService.courseSearch(languageName);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(courseBOList));
        super.safeJsonPrint(response, json);
    }
}