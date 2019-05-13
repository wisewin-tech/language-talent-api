package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.bo.UserStudyDetailsBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.pop.SystemConfig;
import com.wisewin.api.service.UserService;
import com.wisewin.api.service.UserStudyDetailsService;
import com.wisewin.api.util.DateUtils;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.OSSClientUtil;
import com.wisewin.api.util.env.Env;
import com.wisewin.api.util.env.ResourceUtil;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/studyDetails")
public class UserStudyDetailsController extends BaseCotroller {
    @Resource
    private UserStudyDetailsService userStudyDetailsService;
    @Resource
    private UserService userService;


    /**
     * 用户学习详情
     * @param request
     * @param response
     */
    @RequestMapping("/getUserStudyDetails")
    public void getUserStudyDetails(HttpServletRequest request, HttpServletResponse response){
        //获取当前登录用户对象
        UserBO userBO = super.getLoginUser(request);
        if (userBO==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
            //获取当前登录用户id
            Integer userId = userBO.getId();
            Date studyDate = DateUtils.getDateByType(new Date(),"yyyy-MM-dd");
            UserStudyDetailsBO userStudyDetailsBO = userStudyDetailsService.getStudyDetails(userId,studyDate);
            if (userStudyDetailsBO==null){
                userStudyDetailsService.insertDuration(userId);
            }else {
                //获取学习时长
                Integer studyDuration = userStudyDetailsBO.getStudyDuration();
                Env env = new Env();
                Integer pollingFrequency = Integer.parseInt(env.getProperty("pollingFrequency"));
                studyDuration+=pollingFrequency;

                //修改学习时长
                userStudyDetailsService.updateDuration(userId, studyDuration,studyDate);
                UserStudyDetailsBO studyDetailsBO = userStudyDetailsService.getStudyDetails(userId,studyDate);
                UserBO userBO1 = userService.selectById(userId);
                List<UserStudyDetailsBO> weekweekStudyDuration = userStudyDetailsService.weekStudyDuration(userId);
                for (UserStudyDetailsBO userStudyDetailsBO1:weekweekStudyDuration){
                    Date weekStudyDate = DateUtils.getDateByType(userStudyDetailsBO1.getStudyDate(),"MM-dd");
                    userStudyDetailsBO1.setStudyDate(weekStudyDate);
                }

                //连续学习天数
                Integer continuousLearning = userBO1.getContinuousLearning();
                //累计学习天数
                Integer cumulativeLearning = userBO1.getCumulativeLearning();
                Map resultMap = new HashMap();
                //将结果放入map中
                resultMap.put("studyDetailsBO",studyDetailsBO);
                resultMap.put("cumulativeLearning",cumulativeLearning);
                resultMap.put("continuousLearning",continuousLearning);
                resultMap.put("weekweekStudyDuration",weekweekStudyDuration);
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
                super.safeJsonPrint(response, json);
            }

    }

}
