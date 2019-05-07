package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.CouplebackBO;
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
     *   Integer userId; //用户id
     * String content; //反馈内容
     * String contactpattern; //用户联系方式
     * String pictureUrl; //图片url
     */
    @RequestMapping("/addCpupleback")
    public void addCpupleback(HttpServletRequest request, HttpServletResponse response, CouplebackParam param){

        if (param.getUserId()==null && param.getContent().equals("") && param.getContactpattern().equals("") && param.getPictureUrl().equals("")){
            String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response,json);
            return;
        }

        boolean  addCpuplebackjson=couplebackService.getaddCpupleback(param.getUserId(),param.getContent(),param.getContactWay(),param.getContactpattern(),param.getPictureUrl());
        if (addCpuplebackjson){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加成功"));
            super.safeJsonPrint(response, json);
            return;
        }

    }

    /**
     * 显示反馈意见
     *  private Integer id; //意见反馈id
     private String content; //反馈内容
     private String contactpattern; //用户联系方式
     private String pictureUrl; //图片url
     private Date cbUpdatetime; //修改时间
     private String disposeresUlt; //处理结果
     private String disposeperson; //处理人
     */
    @RequestMapping("/queryCpupleback")
    public void queryCpupleback(HttpServletRequest request,HttpServletResponse response,CouplebackParam param){
        if (param.getId()==null && param.getContent().equals("") &&  param.getContactpattern().equals("") && param.getPictureUrl().equals("")){
            String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response,json);
            return;
        }

        List<CouplebackBO> list=couplebackService.getqueryCpupleback(param.getId(),param.getContent(),param.getContactpattern(),
                param.getPictureUrl(),param.getCbUpdatetime(),param.getDisposeresUlt(),param.getDisposeperson());
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, json);
        return;
    }
}
