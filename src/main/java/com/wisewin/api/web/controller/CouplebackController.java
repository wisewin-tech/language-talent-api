package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.CouplebackBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.CouplebackParam;
import com.wisewin.api.service.CouplebackService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/Coupleback")
public class CouplebackController  extends BaseCotroller {

    @Resource
    private CouplebackService couplebackService;


    /**
     * 添加反馈信息
     *  Integer userid; //用户id
     * String content; //反馈内容
     *  String contactpattern; //用户联系方式
     * String pattern; //用户联系
     *  String pictureurl; //图片url
     */
    @RequestMapping("/addCpupleback")
    public void addCpupleback(HttpServletRequest request, HttpServletResponse response, CouplebackParam param){
        UserBO loginUser = super.getLoginUser(request);
        Integer id = loginUser.getId();
        if (id==null || param.getContent().equals("") || param.getPattern().equals("")){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        boolean addCpuplebackjson=couplebackService.getaddCpupleback(id,param.getContent(),param.getContactpattern(),param.getPattern(),param.getPictureurl());
        if (addCpuplebackjson){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("反馈成功"));
            super.safeJsonPrint(response, json);
            return;
        }

        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response, json);
        return;
    }
}
