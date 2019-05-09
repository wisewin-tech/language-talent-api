package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.DictionariesBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.DictionariesService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/Dictionaries")
public class DictionariesController extends BaseCotroller {


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
    public void queryDictionaries(HttpServletRequest request, HttpServletResponse response, Integer id,String key,String value,String outer){



        List<DictionariesBO> list=dictionariesService.getqueryDictionaries(id,key,value,outer);
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response,json);
        return;

    }

}
