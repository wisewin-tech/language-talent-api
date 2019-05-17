package com.wisewin.api.web.controller;
import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.LanguageBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.PruchaseDTO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.PurchaseService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Created by 王彬 on 2019/5/9.
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseCotroller {

    @Resource
    private PurchaseService purchaseService;

    /**
     *
     * @param request
     * @param response
     * @param id  语言或课程id
     * @param state  语言或课程   语言language       课程curriculum
     */
    @RequestMapping("/querypurchase")
    public void purchaseCurriculum(HttpServletRequest request, HttpServletResponse response, String id, String state){
       UserBO userBO = super.getLoginUser(request);
        if(StringUtils.isEmpty(id)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(StringUtils.isEmpty(state)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

       if(userBO==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
       }

       //查询当前用户
        UserBO user  =  purchaseService.selectUser(userBO.getId());
        if(user == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000016"));
            super.safeJsonPrint(response, json);
            return;
        }

        if(state.equals("curriculum")){
         CourseBO course =  purchaseService.queryCouse(id);
         //判断有无此课程
            if(course == null){
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000019"));
                super.safeJsonPrint(response, json);
                return;
            }
           //判断当前用户是否购买过此课程
          /* boolean bl = purchaseService.purchase(user.getId(),id);
            if(bl){
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000017"));
                super.safeJsonPrint(response, json);
                return;
            }*/
            PruchaseDTO pruchase = purchaseService.isCourse(course,user);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(pruchase));
            super.safeJsonPrint(response, json);
            }

        if(state.equals("language")){
          LanguageBO language  =  purchaseService.queryLanguare(id);
            if(language == null){
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000020"));
                super.safeJsonPrint(response, json);
                return;
            }
            PruchaseDTO pruchase = purchaseService.isLanguage(language,user);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(pruchase));
            super.safeJsonPrint(response, json);
           }
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response, json);
        return;
        }
    /*
     *
     * @param request       下订单
     * @param response
     * @param id        语言或课程id
     * @param state      状态  用来区分语言还是课程  语言language       课程curriculum
     */
    @RequestMapping( "/purchaseOder")
    public void purchaseOder(HttpServletRequest request, HttpServletResponse response, String id, String state){
        UserBO userBO = super.getLoginUser(request);
        if(StringUtils.isEmpty(id)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        if(StringUtils.isEmpty(state)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(userBO==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }


        //查询当前用户
        UserBO user  =  purchaseService.selectUser(userBO.getId());
        if(user == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000016"));
            super.safeJsonPrint(response, json);
            return;
        }

        if(state.equals("curriculum")){
            CourseBO course =  purchaseService.queryCouse(id);
            //判断有无此课程
            if(course == null){
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000019"));
                super.safeJsonPrint(response, json);
                return;
            }
             PruchaseDTO pruchase  =  purchaseService.isCourse(course,user);
             //如果咖豆不足，返回
             if(!pruchase.getState()){
                 String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000018"));
                 super.safeJsonPrint(response, json);
                 return;
             }
             //生成订单
            purchaseService.insertOrderCouse(course,user.getId()+"",pruchase);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("购买成功"));
            super.safeJsonPrint(response, json);
        }
        if(state.equals("language")){
            LanguageBO language  =  purchaseService.queryLanguare(id);
            if(language == null){
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000020"));
                super.safeJsonPrint(response, json);
                return;
            }
            PruchaseDTO pruchase  =  purchaseService.isLanguage(language,user);
            //如果咖豆不足，返回
            if(!pruchase.getState()){
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000018"));
                super.safeJsonPrint(response, json);
                return;
            }
            //生成订单
            purchaseService.insertOrderlanguage(language.getId()+"",user.getId()+"",pruchase);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("购买成功"));
            super.safeJsonPrint(response, json);
        }
    }
}

