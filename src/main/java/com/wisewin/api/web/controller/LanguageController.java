package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.LanguageBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.CertificateService;
import com.wisewin.api.service.LanguageService;
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

@Controller
@RequestMapping("/language")
public class LanguageController extends BaseCotroller{

    @Resource
    private LanguageService languageService;
    @Resource
    private CertificateService certificateService;

    @RequestMapping("/languageDetails")
    public void languageDetails(LanguageBO id,HttpServletRequest request, HttpServletResponse response){

        List<LanguageBO> languageBOList = languageService.languageDetails(id);
        for (LanguageBO languageBO: languageBOList){
            String image = certificateService.certificateImage(languageBO.getCertificateId());
            languageBO.setCertificateImage(image);
        }
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(languageBOList));
        super.safeJsonPrint(response, result);
    }
}
