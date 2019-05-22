package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.StudyPlanService;
import com.wisewin.api.service.UserScoreRecordService;
import com.wisewin.api.service.UserService;
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

    @RequestMapping("/getStudyPlan")
    public void studyPlan(Integer levelId, HttpServletRequest request, HttpServletResponse response) {
        if (levelId == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
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

            List<ChapterResultBO> chapterResultBOS = levelBO.getChapterBOList();
            for (ChapterResultBO chapterResultBO : chapterResultBOS) {
                Integer chapterId = chapterResultBO.getChapterId();
                Integer score = userScoreRecordService.getScore(userId, chapterId);
                chapterResultBO.setScore(score);

        }
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(levelBO));
        super.safeJsonPrint(response, json);


    }

    @RequestMapping("/getLevelList")
    public void getLevelList(Integer languageId,HttpServletRequest request,HttpServletResponse response){
        if (languageId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        LanguageResultBO languageResultBO = studyPlanService.getLevelList(languageId);
        List<CourseResultBO> courseResultBOS = languageResultBO.getCourseList();
        for (CourseResultBO courseResultBO:courseResultBOS){
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
