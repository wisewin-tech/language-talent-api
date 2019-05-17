package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.QuestionBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.QuestionService;
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
 * Created by 王彬 on 2019/5/7.
 */
@Controller
@RequestMapping("/question")
public class QuestionController extends BaseCotroller{

    @Resource
    private QuestionService questionService;

    /**
     *   提供习题接口
     *   从redis中查询当前登陆的用户对象
     *   判断参数
     *   根据不同条件返回不同数据
     * @param request
     * @param response
     *  relevanceId      条件关联id
     *  chapterType     学前热身(warmUp) 课后测试（test
     *  testType        语言能力测试（languageTest）课程考证题（courseCertificate）课时测试题（chapterTest）
     */
    @RequestMapping(value = "/listQuestion")
    public void queryWarmUp(HttpServletRequest request, HttpServletResponse response,Integer relevanceId, String questionType, String testType){
       UserBO userBO = super.getLoginUser(request);
        if(userBO == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
      /* if(relevanceId == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(StringUtils.isEmpty(questionType)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);    q
            return;
        }
        if(StringUtils.isEmpty(testType)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }*/

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("relevanceId",relevanceId);
        map.put("questionType",questionType);
        map.put("testType",testType);
        List<QuestionBO> list = questionService.queryQuestionList(map);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, json);
    }
}
