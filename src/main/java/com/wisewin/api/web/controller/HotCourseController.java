package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.CourseService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
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
@RequestMapping("/hotCourse")
public class HotCourseController extends BaseCotroller{
    @Resource
    private CourseService courseService;

    @Resource
    LogService logService;

    @RequestMapping("/getHotCourse")
    public void getHotCourse(Integer pageNo, Integer pageSize,HttpServletRequest request, HttpServletResponse response){
        logService.startController(null,request,pageNo,pageSize);
        QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
        Map<String, Object> maps = new HashMap<String, Object>();
        if(queryInfo != null){
            maps.put("pageOffset", queryInfo.getPageOffset());
            maps.put("pageSize", queryInfo.getPageSize());
        }
        logService.call("courseService.getHotCourse",maps);
        List<CourseBO> hotCourse = courseService.getHotCourse(maps);
        logService.result(hotCourse);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(hotCourse));
        super.safeJsonPrint(response, result);
        logService.end("/hotCourse/getHotCourse");
    }

}
