package com.wisewin.api.web.controller;

import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.OSSClientUtil;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图片上传与删除
 */
@Controller
@RequestMapping("/image")
public class UpPictureController extends BaseCotroller {
    //上传图片
    @RequestMapping("/upImage")
    public void upImage(HttpServletRequest request, HttpServletResponse response, MultipartFile image)
            throws Exception {
        //图片非空判断
        if (image==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response,json);
        }
        OSSClientUtil ossClientUtil=new OSSClientUtil();
        //上传
        String name=ossClientUtil.uploadImg2Oss(image);
        //name:图片路径+图片名(图片名为生成的随机数)
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(name));
        super.safeJsonPrint(response,json);
    }
    //删除图片
    @RequestMapping("/delImage")
    public void delImage(String name,HttpServletResponse response) {

        if (StringUtils.isEmpty(name)){
            String json = JsonUtils.getJsonString4JavaPOJO
                    (ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response,json);
        }

        OSSClientUtil ossClientUtil=new OSSClientUtil();
        ossClientUtil.deleteFileInfo(name);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success
                (null));
        super.safeJsonPrint(response,json);
    }
}
