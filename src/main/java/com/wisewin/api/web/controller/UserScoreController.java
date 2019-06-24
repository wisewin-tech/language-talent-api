package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.UserScoreRecordBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.UserScoreService;
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
 *wy  log
 * */
@Controller
@RequestMapping("/userScore")
public class UserScoreController extends BaseCotroller{

    @Resource
    private UserScoreService userScoreService;

    @Resource
    LogService logService;
    /**
     * 查询用户课时成绩
     * @param response
     * @param request
     */
    @RequestMapping("/selectUserScore")
    public void selectUserScore(Integer chapterId,HttpServletResponse response, HttpServletRequest request){
        logService.startController(null,request,chapterId);
        //从cookie中获取他的user对象的id
        Integer id=this.getId(request);
        //如果获取不到,参数异常
        if (id==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        logService.call("userScoreService.selectScore",id,chapterId);
        List<UserScoreRecordBO> list=userScoreService.selectScore(id,chapterId);
        logService.result(list);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        logService.end("userScore/selectUserScore",json);
        super.safeJsonPrint(response, json);
    }

    /**
     * 添加用户答题记录
     * @param chapterId         课时id
     * @param score             成绩
     * @param response
     * @param request
     */
    @RequestMapping("/addUserScore")
    public void selectUserScore(Integer chapterId,Integer score, HttpServletResponse response, HttpServletRequest request){
        logService.startController(null,request,chapterId);
        //从cookie中获取他的user对象的id
        Integer id=this.getId(request);
        //如果获取不到,参数异常
        if (id==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            logService.end("userScore/addUserScore",json);
        }
        if (chapterId==null||score==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("userScore/addUserScore",json);
        }
        Integer i = userScoreService.getScore(id,chapterId);
        Map<String, Object> condition = new HashMap<String, Object>();
        if (i==null) {

            //吧用户id,课时id,成绩,答题时间放入map中
            condition.put("userId", id);
            condition.put("chapterId", chapterId);
            condition.put("score", score);
            logService.call("userScoreService.addScore", condition);
            userScoreService.addScore(condition);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("课时成绩添加成功"));
            logService.end("/userScore/addUserScore", json);
            super.safeJsonPrint(response, json);
            return;
        }else{
            userScoreService.updateScore(id,chapterId,score);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("课时成绩添加成功"));
            logService.end("/userScore/addUserScore", json);
            super.safeJsonPrint(response, json);
            return;
        }
    }



}
