package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.ShareBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.ShareService;
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

/**
 * 分享图片
 */
@Controller
@RequestMapping("/share")
public class ShareController extends BaseCotroller {
    static final Logger log = LoggerFactory.getLogger(ShareController.class);

    @Resource
    private ShareService shareService;

    @RequestMapping("/queryShare")
    public void queryShare(HttpServletResponse response, HttpServletRequest  request,String key){
       UserBO loginUser = super.getLoginUser(request);
        if(loginUser==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(StringUtils.isEmpty(key)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        ShareBO shareBO = shareService.queryShare(key);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(shareBO));
        super.safeJsonPrint(response, json);

    }


}