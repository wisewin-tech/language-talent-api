package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.ClauseBO;
import com.wisewin.api.entity.bo.CourseDetailsResultBO;
import com.wisewin.api.entity.bo.LanguageDetailsResultBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.ChapterService;
import com.wisewin.api.service.ClauseService;
import com.wisewin.api.service.CourseService;
import com.wisewin.api.service.LanguageService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/other")
public class OhterController extends BaseCotroller {
    @Resource
    private ClauseService  clauseService;
    @Resource
    private LanguageService languageService;
    @Resource
    private CourseService courseService;
    @Resource
    private ChapterService chapterService;

    static final Logger log = LoggerFactory.getLogger(OhterController.class);


    /**
     * 查询购买须知类的东西
     * @param request
     * @param response
     * @param config
     */
    @RequestMapping("/queryConfig")
    public void queryConfig(HttpServletRequest request,HttpServletResponse response,String config) {
        if(StringUtils.isEmpty(config)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        ClauseBO clauseBO = clauseService.selectClauseBOByClassify(config);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(clauseBO));
        super.safeJsonPrint(response, json);
        return;
    }


    /**
     * 查询课程亮点 ||  语言亮点
     * @param request
     * @param response
     * @param type course  || language
     * @param id
     */
    @RequestMapping("/queryLightspot")
    public void queryLightspot(HttpServletRequest request,HttpServletResponse response,String type,Integer id) {
        if(StringUtils.isEmpty(type) || id==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        String msg="";
        //查询课程的
        if("course".equals(type)){
            CourseDetailsResultBO courseDetailsResultBO = courseService.courseDetailsCourse(id);
            if(courseDetailsResultBO!=null){
                msg= courseDetailsResultBO.getCourseLightspot();
            }
        }
        //查询语言的
        if("language".equals(type)){
            LanguageDetailsResultBO language = languageService.languageDetails(id);
            if(language!=null){
                msg= language.getLanguageLightspot();
            }
        }


        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(msg));
        super.safeJsonPrint(response, json);
        return;
    }

    /**
     *  查询文稿
     */
    @RequestMapping("/queryManuscript")
    public void queryManuscript(HttpServletRequest request,HttpServletResponse response,Integer chapterId) {
        if(chapterId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        String manuscript="";
        ChapterBO chapterBO = chapterService.chapterDetails(chapterId);
        if(chapterBO!=null){
            manuscript=chapterBO.getManuscript();
        }

        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(manuscript));
        super.safeJsonPrint(response, json);
        return;

    }

}
