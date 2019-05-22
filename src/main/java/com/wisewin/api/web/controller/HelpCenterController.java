package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.HelpCenterBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.HelpCenterService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/helpCenter")
public class HelpCenterController extends BaseCotroller {
    @Resource
    private HelpCenterService helpCenterService;

    /**
     * 帮助中心标题展示
     *
     * @return
     */
    @RequestMapping("/selectHelpCenter")
    public void selectHelpCenter(HttpServletRequest request,HttpServletResponse response) {
        List<HelpCenterBO> helpCenterBOList = helpCenterService.selectHelpCenter();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(helpCenterBOList));
        super.safeJsonPrint(response, result);
    }

    /**
     * 帮助中心文本展示
     *
     * @param id
     * @return
     */
    @RequestMapping("/getparticulars")
    public void getparticulars(Integer id, HttpServletRequest request, HttpServletResponse response) {
        //参数验证
        if (id==null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            return;
        }
        HelpCenterBO helpCenterBO = helpCenterService.getparticulars(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(helpCenterBO));
        super.safeJsonPrint(response, result);
    }
}
