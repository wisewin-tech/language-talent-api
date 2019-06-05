package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.ChapterService;
import com.wisewin.api.service.OrderService;
import com.wisewin.api.util.ASEUtil;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StsUtil;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    public void get(HttpServletRequest request,HttpServletResponse response,String chapterId) {
        UserBO loginUser =super.getLoginUser(request);
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

        Integer id=null;
        try {
            byte[] decrypt = ASEUtil.decrypt(ASEUtil.parseHexStr2Byte(chapterId));
            id=Integer.parseInt(new String(decrypt));
        }catch (Exception e){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        boolean itWatch = orderService.isItWatch(loginUser.getId(), id);
        if(itWatch){
            Map<String, String> stsMessage = StsUtil.getStsMessage("user"+loginUser.getId().toString());
            String videoPath = chapterService.queryVideoPath(id);
            stsMessage.put("vid",videoPath);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(stsMessage));
            super.safeJsonPrint(response, json);
            return;
        }else{
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000027"));
            super.safeJsonPrint(response, json);
            return;
        }
    }

    /**
     * 获取OSS临时凭证
     */
    @RequestMapping(value = "/getStsOss" , method = RequestMethod.POST)
    public void getOssSts(HttpServletRequest request,HttpServletResponse response) {
        Map<String, String> stsMessage = StsUtil.getStsOss(request.getSession().getId());
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(stsMessage));
        super.safeJsonPrint(response, json);
        return;
    }


    @RequestMapping("/downloadVod")
    public void test(HttpServletResponse response,HttpServletRequest request,String vid){
        UserBO loginUser =super.getLoginUser(request);
        if(loginUser==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(vid==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        try {
            byte[] decrypt = ASEUtil.decrypt(ASEUtil.parseHexStr2Byte(vid));
            if(decrypt!=null){
                boolean itWatch = orderService.isVidWahch(new String(decrypt));
                if(itWatch){
                    Map<String, String> userTest = StsUtil.getStsMessage(loginUser.getId()+"Download");
                    Map<String,String>  map=new HashMap<String, String>();
                    map.put("AccessKeySecret",userTest.get("akScret"));
                    map.put("SecurityToken",userTest.get("stk"));
                    map.put("Expiration",userTest.get("Expiration"));
                    map.put("AccessKeyId",userTest.get("akId"));
                    Map<String,Object>  resultMap=new HashMap<String, Object>();
                    resultMap.put("SecurityTokenInfo",map);
                    resultMap.put("RequestId",userTest.get("reqId"));
                    String jsonString4JavaPOJO = JsonUtils.getJsonString4JavaPOJO(resultMap);
                    super.safeJsonPrint(response, jsonString4JavaPOJO);
                    return;
                }

            }
        }catch (Exception e){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response, json);
        return;


    }
}
