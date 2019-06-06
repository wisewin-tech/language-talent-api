package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.DiscoverResultBO;
import com.wisewin.api.entity.bo.FavoritesResultBO;
import com.wisewin.api.entity.bo.SpecialResultBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.FavoritesService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
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
 * wy log
 * */

@Controller
@RequestMapping("/myFavorites")
public class FavoritesController extends BaseCotroller {
    @Resource
    private FavoritesService favoritesService;

    @Resource
    LogService logService;

    /**
     * 查询用户收藏的课程信息
     * @param pageNo      页数
     * @param pageSize    每页条数
     * @param response
     * @param request
     */
    @RequestMapping("selectHour")
    public void selectHour(Integer pageNo, Integer pageSize,HttpServletResponse response, HttpServletRequest request){
        logService.startController(null,request,pageNo,pageSize);
        Integer userId=super.getId(request);
        if (userId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/myFavorites/selectHour",json);
            return;
        }
        //封装limit条件,pageNo改为页数
        QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if(queryInfo != null){
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageOffset", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }
        //把userId 用户id,放入map集合中
        condition.put("userId",userId);
        logService.call("favoritesService.selectHour",condition);
        List<FavoritesResultBO> list=favoritesService.selectHour(condition);
        logService.result(list);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, json);
        logService.end("/myFavorites/selectHour",json);
        return;

    }
    /**
     * 查询用户收藏的发现信息
     * @param pageNo      页数
     * @param pageSize    每页条数
     * @param response
     * @param request
     */
    @RequestMapping("selectDiscover")
    public void selectDiscover(Integer pageNo, Integer pageSize,HttpServletResponse response, HttpServletRequest request){
        logService.startController(null,request,pageNo,pageSize);
        Integer userId=super.getId(request);
        if (userId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/myFavorites/selectDiscover",json);
            return;
        }
        //封装limit条件,pageNo改为页数
        QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if(queryInfo != null){
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageOffset", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }
        //把userId 用户id,放入map集合中
        condition.put("userId",userId);
        logService.call("favoritesService.selectDiscover",condition);
        List<DiscoverResultBO> list=favoritesService.selectDiscover(condition);
        logService.result(list);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, json);
        logService.end("/myFavorites/selectDiscover",json);
        return;

    }
    /**
     * 查询用户收藏的专题信息
     * @param pageNo      页数
     * @param pageSize    每页条数
     * @param response
     * @param request
     */
    @RequestMapping("selectSubject")
    public void selectSubject(Integer pageNo, Integer pageSize,HttpServletResponse response, HttpServletRequest request){
        logService.startController(null,request,pageNo,pageSize);
        Integer userId=super.getId(request);
        if (userId==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/myFavorites/selectSubject",json);
            return;
        }
        //封装limit条件,pageNo改为页数
        QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if(queryInfo != null){
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageOffset", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }
        //把userId 用户id,放入map集合中
        condition.put("userId",userId);
        logService.call("favoritesService.selectSubject",condition);
        List<SpecialResultBO> list=favoritesService.selectSubject(condition);
        logService.result(list);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, json);
        logService.end("/myFavorites/selectSubject",json);
        return;

    }
    /**
     * 添加收藏/取消收藏
     * @param sourceId 来源id
     * @param sourceId 来源(课时/发现)
     * @param response
     * @param request
     */
    @RequestMapping("insertCollect")
    public void insertCollect(Integer sourceId,String source,HttpServletResponse response, HttpServletRequest request){
        logService.startController(null,request,sourceId,source);
        Integer userId=super.getId(request);
        if (userId==null||sourceId==null||StringUtils.isEmpty(source)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/myFavorites/insertCollect",json);
            return;
        }
        logService.call("favoritesService.insertCollect",userId,sourceId,source);
        if (favoritesService.insertCollect(userId,sourceId,source)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加收藏成功"));
            super.safeJsonPrint(response, json);
            logService.end("/myFavorites/insertCollect",json);
        }else{
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("取消收藏成功"));
            super.safeJsonPrint(response, json);
            logService.end("/myFavorites/insertCollect",json);
            return;
        }


    }

}
