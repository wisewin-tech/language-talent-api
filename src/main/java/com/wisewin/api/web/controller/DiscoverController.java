package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.DiscoverJsonBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.DiscoverParam;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.DiscoverService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.date.DateUtil;
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
@RequestMapping("/Discover")
public class DiscoverController extends BaseCotroller{

    @Resource
    private DiscoverService discoverService;
    @Resource
    LogService logService;


    /**
     * 发现首页
     * Integer id; //发现id
     * String title; //发现标题
     * Integer browse; //浏览人数
     * Date createTime; //发布时间
     * String thumbnail; //缩略图url
     * String video; //视频url
     * String type; //类型](课程,新闻,线下活动)
     * Integer priority; //优先级
     * String stick; //置顶[是与否]
     * String show; //是否显示
     * Integer pageNo; //页数
     * Integer pageSize; //每条条数
     * int  countnum;//总数
     *
     */
    @RequestMapping("/queryDiscover")
    public void queryDiscover(HttpServletRequest request, HttpServletResponse response, DiscoverParam param){
        logService.startController(null,request,param.toString());
        //分页
        QueryInfo queryInfo=getQueryInfo(param.getPageNo(),param.getPageSize());
        if (queryInfo!=null){
            param.setPageNo(queryInfo.getPageOffset());
            param.setPageSize(queryInfo.getPageSize());
        }

         //查询文章 视频  做分页   list
        logService.call("discoverService.getqueryDiscover",param.toString());
        List<DiscoverJsonBO> discoverPage=discoverService.getqueryDiscover(param);
        logService.result(discoverPage.toString());
        //线下活动
        logService.call("discoverService.getqueryDiscovertype",param.toString());
        List<DiscoverJsonBO> activity=discoverService.getqueryDiscovertype(param);
        logService.result(activity.toString());
        //首页分页条件查询总数
        logService.call("discoverService.getfindcoun",param.toString());
        int count=discoverService.getfindcoun(param.getCountnum());
        logService.result(count);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("discoverPage",discoverPage);
        map.put("activity",activity);
        map.put("count",count);

        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response,json);
        logService.end("/Discover/queryDiscover",json);
        return;
    }



    /**
     * 详情
     * Integer id; //发现id
     * String title; //发现标题
     * Integer browse; //浏览人数
     * Date createTime; //发布时间
     * String video; //视频url
     * String thumbnail; //缩略图url
     * String content; //内容
     * String type; //类型](课程,新闻,线下活动)
     * Integer likenum; //喜欢人数
     * Integer participation; //要参与
     * Date activitytime; //活动时间
     * String activitysite; //活动地址
     * String phone; //联系电话
     * double ticket; //在线购票
     * String skip; //跳转url[线下活动]
     */

    @RequestMapping("/queryDiscoveractivity")
    public void queryDiscoveractivity(HttpServletRequest request,HttpServletResponse response, DiscoverParam param) {
        logService.startController(null,request,param.toString());
        if (param.getId()== null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/Discover/queryDiscoveractivity",json);
            return;
        }

        //根据id来查找发现详情
        //线下活动和文章二为合一
        logService.call("discoverService.getqueryDiscoveractivity",param.getId());
        DiscoverBO list=discoverService.getqueryDiscoveractivity(param.getId());
        logService.result(list.toString());
        if (list==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/Discover/queryDiscoveractivity",json);
            return;
        }
        //浏览次数加一
        Integer browse= list.getBrowse()+1;
        //进行修改浏览次数
        logService.call("discoverService.getupdateDiscover",param.getId(),browse);
        boolean updatediscover=discoverService.getupdateDiscover(param.getId(),browse);
        logService.result(updatediscover);
        if (updatediscover){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
            super.safeJsonPrint(response, json);
            logService.end("/Discover/queryDiscoveractivity",json);
            return;
        }
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response, json);
        logService.end("/Discover/queryDiscoveractivity",json);
        return;
    }

    /**
     * 发现更多
     */
    @RequestMapping("/queryfindDiscover")
    public void queryfindDiscover(HttpServletRequest request,HttpServletResponse response,DiscoverParam param){
        logService.startController(null,request,param.toString());
        //根据类型查找
        //显示更多，以防发现更多里面出现现在浏览的文章，在sql里面做处理
        logService.call("discoverService.getqueryfindDiscover",param.toString());
        List<DiscoverParam> list=discoverService.getqueryfindDiscover(param);
        logService.result(list.toString());
        if (list.equals("")){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/Discover/queryfindDiscover",json);
            return;
        }
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response,json);
        logService.end("/Discover/queryfindDiscover",json);
        return;
    }

}
