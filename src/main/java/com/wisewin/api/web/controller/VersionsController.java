package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.VersionsBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.VersionsService;
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
 * wy log
 * */
@Controller
@RequestMapping("/Versions")
public class VersionsController extends BaseCotroller {

    @Resource
    private VersionsService versionsService;

    @Resource
    LogService logService;
    /**
     * 通过版本查询
     */
    @RequestMapping("/queryVersions")
    public void queryVersions(HttpServletRequest request,HttpServletResponse response){
        //根据版本号来查询
        logService.startController(null,request,null);
        logService.call("versionsService.getqueryVersions");
        List<VersionsBO> queryVersionsjson=versionsService.getqueryVersions();
        logService.result(queryVersionsjson);
        String json=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(queryVersionsjson));
        super.safeJsonPrint(response,json);
        logService.end("/Versions/queryVersions",json);
        return;
    }
}
