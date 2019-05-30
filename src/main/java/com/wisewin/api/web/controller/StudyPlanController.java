package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.*;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * 获取学习计划
     * @param levelId 级别id
     * @param request
     * @param response
     */
    @RequestMapping("/getStudyPlan")
    public void studyPlan(Integer levelId, HttpServletRequest request, HttpServletResponse response) {
        if (levelId == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        //获取登录用户信息
        UserBO userBO = super.getLoginUser(request);
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
        LevelBO levelBO = studyPlanService.getStudyPlan(languageId, levelId);
        Integer courseId = courseService.getCourseIdByLevelId(levelId);

        if (levelBO!=null){
            Integer count = orderService.getStatusByCourseId(userId,courseId);
            if (count>0){
                levelBO.setBuyOrNot("yes");
            }else {
                levelBO.setBuyOrNot("no");
            }
            List<ChapterResultBO> chapterResultBOS = levelBO.getChapterBOList();
            for (ChapterResultBO chapterResultBO : chapterResultBOS) {
                Integer chapterId = chapterResultBO.getChapterId();
                Integer score = userScoreRecordService.getScore(userId, chapterId);
                chapterResultBO.setScore(score);
            }
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(levelBO));
            super.safeJsonPrint(response, json);
            return;
        }

        Integer levelId1 = studyPlanService.getLevelIdByOne();
        Integer courseId1 = courseService.getCourseIdByLevelId(levelId1);
        Integer count = orderService.getStatusByCourseId(userId,courseId1);
        LevelBO levelBO1 = studyPlanService.getStudyPlan(languageId,levelId1);
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
        if (languageId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        //获取登录对象
        UserBO userBO = super.getLoginUser(request);

        LanguageResultBO languageResultBO = studyPlanService.getLevelList(languageId);
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
            Integer count = orderService.getStatusByCourseId(userId,courseId);
            if (count>0){
                courseResultBO.setBuyOrNot("yes");
            }else {
                courseResultBO.setBuyOrNot("no");
            }
            List<LevelResultBO> levelResultBOS =  courseResultBO.getLevelList();
               for (LevelResultBO levelResultBO:levelResultBOS){
                   Integer i = studyPlanService.getLevelCount(levelResultBO.getLevelId());
                   levelResultBO.setChapterCount(i);
               }
        }

        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(languageResultBO));
        super.safeJsonPrint(response, json);

    }


}
