package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.*;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * log wy
 * */
@Controller
@RequestMapping("/studyPlan")
public class StudyPlanController extends BaseCotroller {
    @Resource
    private StudyPlanService studyPlanService;
    @Resource
    private UserScoreRecordService userScoreRecordService;
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;
    @Resource
    private CourseService courseService;

    @Resource
    LogService logService;

    /**
     * 获取学习计划
     * @param levelId 级别id
     * @param request
     * @param response
     */
    @RequestMapping("/getStudyPlan")
    public void studyPlan(Integer levelId, HttpServletRequest request, HttpServletResponse response) {
        //获取登录用户信息
        UserBO userBO = super.getLoginUser(request);
        logService.startController(userBO,request,levelId.toString());
        if (levelId == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        if (userBO == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        Integer userId = userBO.getId();
        UserBO userBO1 = userService.selectById(userId);
        //获取当前登录用户正在学习的语言id
        Integer languageId = userBO1.getStudyingLanguageId();
        if (languageId == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000022"));
            super.safeJsonPrint(response, json);
            return;
        }
        //根据语言id查找课时内容
        logService.call("studyPlanService.getStudyPlan",languageId.toString(),levelId.toString());
        LevelBO levelBO = studyPlanService.getStudyPlan(languageId, levelId);
        logService.end("studyPlanService.getStudyPlan",levelBO.toString());

        logService.call("courseService.getCourseIdByLevelId",levelId.toString());
        Integer courseId = courseService.getCourseIdByLevelId(levelId);
        logService.end("courseService.getCourseIdByLevelId",courseId.toString());

        if (levelBO!=null){
            logService.call("orderService.getStatusByCourseId",userId.toString(),courseId.toString());
            Integer count = orderService.getStatusByCourseId(userId,courseId);
            logService.end("orderService.getStatusByCourseId",count.toString());
            if (count>0){
                levelBO.setBuyOrNot("yes");
            }else {
                levelBO.setBuyOrNot("no");
            }
            List<ChapterResultBO> chapterResultBOS = levelBO.getChapterBOList();
            for (ChapterResultBO chapterResultBO : chapterResultBOS) {
                Integer chapterId = chapterResultBO.getChapterId();
                logService.call("userScoreRecordService.getScore",userId.toString(),chapterId.toString());
                Integer score = userScoreRecordService.getScore(userId, chapterId);
                logService.call("orderService.getStatusByCourseId",score.toString());
                chapterResultBO.setScore(score);
            }
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(levelBO));
            super.safeJsonPrint(response, json);
            return;
        }
        logService.call("studyPlanService.getLevelIdByOne");
        Integer levelId1 = studyPlanService.getLevelIdByOne();
        logService.end("studyPlanService.getLevelIdByOne",levelId1.toString());
        logService.call("courseService.getCourseIdByLevelId",levelId1.toString());
        Integer courseId1 = courseService.getCourseIdByLevelId(levelId1);
        logService.end("courseService.getCourseIdByLevelId",courseId1.toString());
        logService.call("orderService.getStatusByCourseId",userId.toString(), courseId1.toString());
        Integer count = orderService.getStatusByCourseId(userId,courseId1);
        logService.end("orderService.getStatusByCourseId",count.toString());
        logService.call("studyPlanService.getStudyPlan",languageId.toString(),levelId1.toString());
        LevelBO levelBO1 = studyPlanService.getStudyPlan(languageId,levelId1);
        logService.end("studyPlanService.getStudyPlan",levelBO1.toString());
        if (levelBO1!=null) {
            if (count > 0) {
                levelBO1.setBuyOrNot("yes");
            } else {
                levelBO1.setBuyOrNot("no");
            }
        }

            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(levelBO1));
            super.safeJsonPrint(response, json);
    }

    /**
     * 学习计划右侧边栏获取语言下的级别列表
     * @param languageId 语言id
     * @param request
     * @param response
     */
    @RequestMapping("/getLevelList")
    public void getLevelList(Integer languageId,HttpServletRequest request,HttpServletResponse response){
        //获取登录对象
        UserBO userBO = super.getLoginUser(request);
        logService.startController(userBO,request,languageId.toString());
        if (languageId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        logService.call("studyPlanService.getLevelList",languageId.toString());
        LanguageResultBO languageResultBO = studyPlanService.getLevelList(languageId);
        logService.end("studyPlanService.getLevelList",languageResultBO.toString());
        if (languageResultBO==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(languageResultBO));
            super.safeJsonPrint(response, json);
            return;
        }
        List<CourseResultBO> courseResultBOS = languageResultBO.getCourseList();
        for (CourseResultBO courseResultBO:courseResultBOS){
            //获取用户id
            Integer userId = userBO.getId();
            Integer courseId = courseResultBO.getCourseId();
            logService.call("orderService.getStatusByCourseId",userId.toString(),courseId.toString());
            Integer count = orderService.getStatusByCourseId(userId,courseId);
            logService.call("orderService.getStatusByCourseId",count.toString());

            if (count>0){
                courseResultBO.setBuyOrNot("yes");
            }else {
                courseResultBO.setBuyOrNot("no");
            }
            List<LevelResultBO> levelResultBOS =  courseResultBO.getLevelList();
               for (LevelResultBO levelResultBO:levelResultBOS){
                   logService.call("studyPlanService.getLevelCount",levelResultBO.getLevelId().toString());
                   Integer i = studyPlanService.getLevelCount(levelResultBO.getLevelId());
                   logService.end("studyPlanService.getLevelCount",i.toString());

                   levelResultBO.setChapterCount(i);
               }
        }

        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(languageResultBO));
        super.safeJsonPrint(response, json);

    }


}
