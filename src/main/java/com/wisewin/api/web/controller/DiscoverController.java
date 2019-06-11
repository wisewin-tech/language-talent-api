package com.wisewin.api.web.controller;

import com.wisewin.api.dao.FavoritesDAO;
import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.DiscoverJsonBO;
import com.wisewin.api.entity.bo.MyFavoriteBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.DiscoverParam;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.DiscoverService;
import com.wisewin.api.service.FavoritesService;
import com.wisewin.api.service.LikerelatioService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
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
    @Resource
    private LikerelatioService likerelatioService;

    @Resource
    FavoritesService favoritesService;

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
        logService.startController(null,request,param);
        //分页
        QueryInfo queryInfo=getQueryInfo(param.getPageNo(),param.getPageSize());
        if (queryInfo!=null){
            param.setPageNo(queryInfo.getPageOffset());
            param.setPageSize(queryInfo.getPageSize());
        }

         //查询文章 视频  做分页   list
        logService.call("discoverService.getqueryDiscover",param);
        List<DiscoverJsonBO> discoverPage=discoverService.getqueryDiscover(param);
        logService.result(discoverPage);
        //线下活动
        logService.call("discoverService.getqueryDiscovertype",param);
        List<DiscoverJsonBO> activity=discoverService.getqueryDiscovertype(param);
        logService.result(activity);
        //首页分页条件查询总数
        logService.call("discoverService.getfindcoun",param);
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
        logService.startController(null,request,param);
        if (param.getId()== null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/Discover/queryDiscoveractivity",json);
            return;
        }
        //获取当前用户id
        UserBO loginUser = super.getLoginUser(request);

        if (StringUtils.isObjEmpty(loginUser)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        Integer id = loginUser.getId();

        //进行修改浏览次数
        if(param.getSource().equals("html")){
            logService.call("discoverService.getupdateDiscover",param.getId());
            discoverService.getupdateDiscover(param.getId());
        }

        //根据id来查找发现详情
        //线下活动和文章二为合一
        logService.call("discoverService.getqueryDiscoveractivity",param.getId());
        DiscoverBO discoverBO=discoverService.getqueryDiscoveractivity(param.getId());
        logService.result(discoverBO);
        if (discoverBO==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            logService.end("/Discover/queryDiscoveractivity",json);
            return;
        }



        //查询是否喜欢过
        boolean likeBool=likerelatioService.getfindLikerelation(id,param.getId());
        String like=likeBool?"yes":"no";
        discoverBO.setLike(like);


        //查询是否收藏过
        boolean collectionBool=favoritesService.isCollection(id,param.getId(),"discover");
        String collection=collectionBool?"yes":"no";
        discoverBO.setCollection(collection);

        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(discoverBO));
        super.safeJsonPrint(response, json);

        logService.end("/Discover/queryDiscoveractivity",json);
        return;
    }

    /**
     * 发现更多
     */
    @RequestMapping("/queryfindDiscover")
    public void queryfindDiscover(HttpServletRequest request,HttpServletResponse response,DiscoverParam param){
        logService.startController(null,request,param);
        //根据类型查找
        //显示更多，以防发现更多里面出现现在浏览的文章，在sql里面做处理
        logService.call("discoverService.getqueryfindDiscover",param);
        List<DiscoverParam> list=discoverService.getqueryfindDiscover(param);
        logService.result(list);
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response,json);
        logService.end("/Discover/queryfindDiscover",json);
        return;
    }

}
