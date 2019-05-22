package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.ChapterService;
import com.wisewin.api.service.OrderService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StsUtil;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/sequence")
public class SequenceController extends BaseCotroller {

    @Resource
    private OrderService  orderService;
    @Resource
    private ChapterService  chapterService;

    /**
     *  获取和播放凭证
     * @param request
     * @param response
     * @param chapterId  课时id
     */
    @RequestMapping(value = "/get" , method = RequestMethod.POST)
    public void get(HttpServletRequest request,HttpServletResponse response,Integer chapterId) {
        UserBO loginUser = super.getLoginUser(request);
        if(loginUser==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }

        if(chapterId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        boolean itWatch = orderService.isItWatch(loginUser.getId(), chapterId);
        if(itWatch){
            Map<String, String> stsMessage = StsUtil.getStsMessage(loginUser.getId().toString());
            ChapterBO chapterBO = chapterService.chapterDetails(chapterId);
            stsMessage.put("vid",chapterBO.getVideoPath());
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(stsMessage));
            super.safeJsonPrint(response, json);
            return;
        }else{
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000027"));
            super.safeJsonPrint(response, json);
        }
    }

}