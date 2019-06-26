package com.wisewin.api.web.controller;
import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.LanguageBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.PruchaseDTO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.PurchaseService;
import com.wisewin.api.util.AgentUserKit;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.RequestUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 王彬 on 2019/5/9.
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseCotroller {

    static final Logger log = LoggerFactory.getLogger(PurchaseController.class);

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
        log.info("start==========================com.wisewin.api.web.controller.PurchaseController.purchaseCurriculum======================");
        log.info("请求ip{}", RequestUtils.getIpAddress(request));
        log.info("请求id{}",id);
        log.info("请求state{}",state);
        UserBO userBO = super.getLoginUser(request);
        if(StringUtils.isEmpty(id)){
            log.info("StringUtils.isEmpty(id),return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(StringUtils.isEmpty(state)){
            log.info("StringUtils.isEmpty(state),return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

       if(userBO == null){
           log.info("userBO==null,return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
       }

       //查询当前用户
        UserBO user  =  purchaseService.selectUser(userBO.getId());
        if(user == null){
            log.info("user == null,return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000016"));
            super.safeJsonPrint(response, json);
            return;
        }

        if(state.equals("curriculum")){
            log.info("state.equals(\"curriculum\"),return");
           CourseBO course =  purchaseService.queryCouse(id);
          //判断有无此课程
            if(course == null){
                log.info("course == null");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000019"));
                super.safeJsonPrint(response, json);
                return;
            }
            PruchaseDTO pruchase = purchaseService.isCourse(course,user);
            if(pruchase == null){
                log.info("pruchase == null,return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000050"));
                super.safeJsonPrint(response, json);
                return;
            }
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(pruchase));
            log.info("return{}",json);
            log.info("end=======================com.wisewin.api.web.controller.PurchaseController.purchaseCurriculum===========================");
            super.safeJsonPrint(response, json);
            return;
            }
        if(state.equals("language")){
            log.info("state.equals(\"language\")");
          LanguageBO language  =  purchaseService.queryLanguare(id);
            if(language == null){
                log.info("language == null,return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000020"));
                super.safeJsonPrint(response, json);
                return;
            }
            PruchaseDTO pruchase = purchaseService.isLanguage(language,user);
            log.info("pruchase:{}",pruchase);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(pruchase));
            log.info("return{}",json);
            log.info("end=======================com.wisewin.api.web.controller.PurchaseController.purchaseCurriculum===========================");
            super.safeJsonPrint(response, json);
            return;
           }
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        log.info("return{}",json);
        log.info("end=======================com.wisewin.api.web.controller.PurchaseController.purchaseCurriculum===========================");
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
    public void purchaseOder(HttpServletRequest request, HttpServletResponse response, String id, String state) throws ParseException {
        log.info("start====================================com.wisewin.api.web.controller.PurchaseController.purchaseOder==========================");
        log.info("请求ip{}",RequestUtils.getIpAddress(request));
        log.info("参数id{}",id);
        log.info("参数state{}",state);
        UserBO userBO = super.getLoginUser(request);
        if(StringUtils.isEmpty(id)){
            log.info("StringUtils.isEmpty(id),return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        if(StringUtils.isEmpty(state)){
            log.info("StringUtils.isEmpty(state),return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(userBO==null){
            log.info("userBO==null,return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        //查询当前用户
            UserBO user  =  purchaseService.selectUser(userBO.getId());
            if(user == null){
                log.info("user == null,return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000016"));
                super.safeJsonPrint(response, json);
                return;
            }
            //获取手机系统
            String model=AgentUserKit.getDeviceInfo(request);


            if(state.equals("curriculum")){
            log.info("state.equals(\"curriculum\")");
            CourseBO course =  purchaseService.queryCouse(id);
            //判断有无此课程
            if(course == null){
                log.info("course == null,return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000019"));
                super.safeJsonPrint(response, json);
                return;
            }

             PruchaseDTO pruchase  =  purchaseService.isCourse(course,user);
             //如果咖豆不足，返回
             if(!pruchase.getState()){
                 log.info("如果咖豆不足，返回");
                 log.info("!pruchase.getState(),return");
                 String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000018"));
                 super.safeJsonPrint(response, json);
                 return;
             }
             //生成订单
            purchaseService.insertOrderCouse(course,user.getId()+"",pruchase, model);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("购买成功"));
            super.safeJsonPrint(response, json);
            log.info("end=========================com.wisewin.api.web.controller.PurchaseController.purchaseOder============================");
            log.info("return,{}",json);
            return;
        }
        if(state.equals("language")){
            LanguageBO language  =  purchaseService.queryLanguare(id);
            if(language == null){
                log.info("language == null,return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000020"));
                super.safeJsonPrint(response, json);
                return;
            }
            PruchaseDTO pruchase  =  purchaseService.isLanguage(language,user);
            //如果咖豆不足，返回
            if(!pruchase.getState()){
                log.info("如果咖豆不足，返回");
                log.info("!pruchase.getState(),return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000018"));
                super.safeJsonPrint(response, json);
                return;
            }
            //生成订单
            purchaseService.insertOrderlanguage(language.getId()+"",user.getId()+"",pruchase);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("购买成功"));
            super.safeJsonPrint(response, json);
            log.info("return,{}",json);
            log.info("end=========================com.wisewin.api.web.controller.PurchaseController.purchaseOder============================");
            return;
        }
    }
}

