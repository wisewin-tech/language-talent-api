package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.UserBO;
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
    public void studyPlan(HttpServletRequest request, HttpServletResponse response){
        UserBO userBO = super.getLoginUser(request);
        if(userBO==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
            Integer userId = userBO.getId();
            UserBO userBO1 = userService.selectById(userId);
            //获取当前登录用户正在学习的语言id
            Integer languageId = userBO1.getStudyingLanguageId();
            if (languageId==null){
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000022"));
                super.safeJsonPrint(response, json);
                return;
            }
            //根据语言id查找课时内容
            List<ChapterBO> chapterBOList = studyPlanService.getStudyPlan(languageId);
            for(ChapterBO chapterBO:chapterBOList){
                Integer chapterId = chapterBO.getChapterId();
                Integer score = userScoreRecordService.getScore(userId,chapterId);
                chapterBO.setScore(score);
            }
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(chapterBOList));
            super.safeJsonPrint(response, json);



    }


}
