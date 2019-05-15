package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.DiscoverJsonBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.DiscoverParam;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.DiscoverService;
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

@Controller
@RequestMapping("/Discover")
public class DiscoverController extends BaseCotroller{

    @Resource
    private DiscoverService discoverService;


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
     */
    @RequestMapping("/queryDiscover")
    public void queryDiscover(HttpServletRequest request, HttpServletResponse response, DiscoverParam param){


        //分页
        QueryInfo queryInfo=getQueryInfo(param.getPageNo(),param.getPageSize());
        if (queryInfo!=null){
            param.setPageNo(queryInfo.getPageOffset());
            param.setPageSize(queryInfo.getPageSize());
        }
         //查询文章 视频  做分页   list
         //2.线下活动  全部   list
            //返回一个map     put .list  put.list2
        List<DiscoverJsonBO> discoverPage=discoverService.getqueryDiscover(param);


        List<DiscoverJsonBO> activity=discoverService.getqueryDiscovertype(param);

        int count=discoverService.getfindcoun(param.getCountnum());
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("discoverPage",discoverPage);
        map.put("activity",activity);
        map.put("count",count);

        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response,json);
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

        if (param.getId()== null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        DiscoverBO list=discoverService.getqueryDiscoveractivity(param.getId(),param.getTitle(),param.getBrowse(),DateUtil.getDate(param.getCreateTime()),
                param.getThumbnail(),param.getVideo(),param.getContent(),param.getType(),param.getLikenum(),param.getParticipation(),DateUtil.getDate(param.getActivitytime()),
                        param.getActivitysite(),param.getPhone(),param.getTicket(),param.getSkip(),param.getVideoImg());

        if (list==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        Integer browse= list.getBrowse()+1;
        boolean updatediscover=discoverService.getupdateDiscover(param.getId(),browse);
        if (updatediscover){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
            super.safeJsonPrint(response, json);
            return;
        }
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response, json);
        return;
    }

    /**
     * 发现更多
     */
    @RequestMapping("/queryfindDiscover")
    public void queryfindDiscover(HttpServletRequest request,HttpServletResponse response,DiscoverParam param){

        List<DiscoverParam> list=discoverService.getqueryfindDiscover(param);
        if (list.equals("")){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response,json);
        return;
    }

}
