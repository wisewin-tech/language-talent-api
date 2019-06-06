package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.DictionariesBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.DictionariesService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * wy log
 * */
@Controller
@RequestMapping("/Dictionaries")
public class DictionariesController extends BaseCotroller {


    @Resource
    LogService logService;

    @Resource
    private DictionariesService dictionariesService;

    /**
     * 根据outer连接字典类型表来查找数据
     *     Integer id; //字典id
     *     String key; //类型名字
     *    String value; //类型
     *    String outer;连接字典类型表valuename
     */
    @RequestMapping("/queryDictionaries")
    public void queryDictionaries(HttpServletRequest request, HttpServletResponse response,String outer){
        logService.startController(null,request,outer);
        logService.call("dictionariesService.getqueryDictionaries",outer);
        List<DictionariesBO> list=dictionariesService.getqueryDictionaries(outer);
        logService.result(list.toString());
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        logService.end("/Dictionaries/queryDictionaries",json);
        super.safeJsonPrint(response,json);
        return;

    }

}
