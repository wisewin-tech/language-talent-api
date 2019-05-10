package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.SpecialBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.SpecialService;
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
 * 专题分类
 *
 */
@Controller
@RequestMapping("/Special")
public class SpecialController extends BaseCotroller {

    @Resource
    SpecialService specialService;

    /**
     * 按分类 查看专题
     * * */
    @RequestMapping("selectSpecialBO")
    public void selectSpecialBO(HttpServletRequest request, HttpServletResponse response,Integer classId){
        if(classId==null||classId==0){
            String languagejson=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response,languagejson);
            return;
        }
        List<SpecialBO> specialBOList=specialService.selectSpecialBO(classId);
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(specialBOList));
        super.safeJsonPrint(response,json);
    }

    /**
     * 用户点进去了一个专题
     * */
    @RequestMapping("selectSpecialBOById")
    public void selectSpecialBOById(HttpServletRequest request, HttpServletResponse response,Integer specialId){
        if(specialId==null||specialId==0){
            String languagejson=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response,languagejson);
            return;
        }
        Integer userId=this.getId(request);
        SpecialBO specialBO = specialService.selectSpecialBOById(1,specialId);//点进去查看的专题
        List<SpecialBO> specialBOList = specialService.selectOtherSpecialBO(specialBO.getClassId(),specialBO.getId());//专题详情页下的其他专题
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("specialBO",specialBO);
        map.put("specialBOList",specialBOList);
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response,json);
    }

    /**
     * 用户点击了喜欢 可以喜欢或者取消喜欢
     * */
    @RequestMapping("updSpecialLikeUser")
    public void updSpecialLikeUser(HttpServletRequest request, HttpServletResponse response,Integer specialId){
        if(specialId==null||specialId==0){
            String languagejson=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response,languagejson);
            return;
        }
        Integer userId=this.getId(request);
        if(specialService.updSpecialLikeUser(1,specialId)){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null)) ;
            super.safeJsonPrint(response , result);
        }else {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001")) ;
            super.safeJsonPrint(response , result);
        }
    }


}
