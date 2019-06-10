package com.wisewin.api.web.controller;

import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.OSSClientUtil;
import com.wisewin.api.util.RequestUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图片上传与删除
 * wy log
 */
@Controller
@RequestMapping("/image")
public class UpPictureController extends BaseCotroller {
    static final Logger log = LoggerFactory.getLogger(UpPictureController.class);
    @Resource
    LogService logService;

    //上传图片
    @RequestMapping("/upImage")
    public void upImage(HttpServletRequest request, HttpServletResponse response, MultipartFile image)
            throws Exception {
        logService.startController(null,request,image);
        //图片非空判断
        if (image==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response,json);
        }
        OSSClientUtil ossClientUtil=new OSSClientUtil();
        //上传
        logService.call("ossClientUtil.uploadImg2Oss",image);
        String name=ossClientUtil.uploadImg2Oss(image);
        logService.result(name);
        //name:图片路径+图片名(图片名为生成的随机数)
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(name));
        logService.end("/image/upImage",json);
        super.safeJsonPrint(response,json);
    }
    //删除图片
    @RequestMapping("/delImage")
    public void delImage(String name,HttpServletResponse response,HttpServletRequest request) {
        logService.startController(null,request,name);
        if (StringUtils.isEmpty(name)){
            String json = JsonUtils.getJsonString4JavaPOJO
                    (ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response,json);
            logService.end("/image/delImage",json);
        }

        OSSClientUtil ossClientUtil=new OSSClientUtil();
        logService.call("ossClientUtil.deleteFileInfo",name);
        ossClientUtil.deleteFileInfo(name);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success
                (null));
        super.safeJsonPrint(response,json);
        logService.end("/image/delImage",json);
    }



}



