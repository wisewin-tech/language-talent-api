package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.bo.UserStudyDetailsBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.pop.SystemConfig;
import com.wisewin.api.service.UserService;
import com.wisewin.api.service.UserStudyDetailsService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.date.DateUtil;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *  log
 * */
@Controller
@RequestMapping("/studyDetails")
public class UserStudyDetailsController extends BaseCotroller {
    @Resource
    private UserStudyDetailsService userStudyDetailsService;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;
    /**
     * 用户学习详情
     * @param request
     * @param response
     */
    @RequestMapping("/getUserStudyDetails")
    public void getUserStudyDetails(HttpServletRequest request, HttpServletResponse response){
        //获取当前登录用户对象
        UserBO userBO = super.getLoginUser(request);
        logService.startController(userBO,request);
        if (userBO==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
            //获取当前登录用户id
            Integer userId = userBO.getId();
        //获取昨天的日期
        String yesterday = DateUtil.getYseterday();
        logService.call("UserStudyDetailsService.getStudyDetails",userId.toString(),yesterday);
        UserStudyDetailsBO yesterdayStudyDetails = userStudyDetailsService.getStudyDetails(userId, yesterday);
        logService.end("UserStudyDetailsService.getStudyDetails",yesterdayStudyDetails.toString());
        //获取今天的日期
        String today = DateUtil.getDateStr(new Date());
        logService.call("UserStudyDetailsService.getStudyDetails",userId.toString(),yesterday);
        UserStudyDetailsBO todayStudyDetails = userStudyDetailsService.getStudyDetails(userId, today);
        logService.end("UserStudyDetailsService.getStudyDetails",yesterdayStudyDetails.toString());
        String studyDate= DateUtil.getDateStr(new Date());
        logService.call("UserStudyDetailsService.getStudyDetails",userId.toString(),yesterday);
        UserStudyDetailsBO userStudyDetailsBO = userStudyDetailsService.getStudyDetails(userId,studyDate);
        logService.end("UserStudyDetailsService.getStudyDetails",yesterdayStudyDetails.toString());
            if (userStudyDetailsBO==null){
                logService.call("UserStudyDetailsService.insertDuration",userId.toString(),new Date().toString());
                userStudyDetailsService.insertDuration(userId,new Date());
            }else {
                //获取学习时长
                Integer studyDuration = userStudyDetailsBO.getStudyDuration();

                Integer pollingFrequency = Integer.parseInt(SystemConfig.getString("pollingFrequency"));
                studyDuration += pollingFrequency;

                //修改学习时长
                logService.call("UserStudyDetailsService.updateDuration",userId.toString(),studyDuration.toString(),studyDate.toString());
                userStudyDetailsService.updateDuration(userId, studyDuration, studyDate);

                logService.call("UserStudyDetailsService.getStudyDetails",userId.toString(),studyDate.toString());
                UserStudyDetailsBO studyDetailsBO1 = userStudyDetailsService.getStudyDetails(userId, studyDate);
                logService.end("UserStudyDetailsService.getStudyDetails",studyDetailsBO1.toString());
            }
                logService.call("userService.selectById",userId.toString());
                UserBO userBO1 = userService.selectById(userId);
                logService.end("userService.selectById",userBO1.toString());
                //获取近一周的所有日期
                DateUtil dateUtil = new DateUtil();
                List<Date> studyDates = dateUtil.getDays(7);
                List<UserStudyDetailsBO> userStudyDetailsBOList = new ArrayList<UserStudyDetailsBO>();
                for (Date date:studyDates){
                    //获取近一周的学习情况
                    logService.call("userStudyDetailsService.weekStudyDuration",userId.toString(),date.toString());
                    UserStudyDetailsBO weekStudyDuration = userStudyDetailsService.weekStudyDuration(userId,date);
                    logService.end("userStudyDetailsService.weekStudyDuration",weekStudyDuration.toString());
                    if (weekStudyDuration!=null) {
                        userStudyDetailsBOList.add(weekStudyDuration);
                    } else {
                        UserStudyDetailsBO userStudyDetailsBO1 = new UserStudyDetailsBO();
                        userStudyDetailsBO1.setStudyDuration(0);
                        userStudyDetailsBO1.setStudyDate(date);
                        userStudyDetailsBO1.setUserId(userId);
                        userStudyDetailsBOList.add(userStudyDetailsBO1);
                    }

                }
                //连续学习天数
                Integer continuousLearning = userBO1.getContinuousLearning();
                //累计学习天数
                Integer cumulativeLearning = userBO1.getCumulativeLearning();


                if (todayStudyDetails==null){
                    continuousLearning =continuousLearning+ 1;
                    cumulativeLearning = cumulativeLearning + 1;
                    userService.updateUserStudyDays(continuousLearning,cumulativeLearning,userId);
                }else {
                    if (yesterdayStudyDetails==null){
                        continuousLearning = 1;
                        cumulativeLearning = cumulativeLearning + 1;
                        userService.updateUserStudyDays(continuousLearning,cumulativeLearning,userId);
                    }
                }
                Map resultMap = new HashMap();
                //将结果放入map中
                //resultMap.put("studyDetailsBO",studyDetailsBO);
                resultMap.put("cumulativeLearning",cumulativeLearning);
                resultMap.put("continuousLearning",continuousLearning);
                resultMap.put("weekStudyDuration",userStudyDetailsBOList);
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
                super.safeJsonPrint(response, json);


    }

}
