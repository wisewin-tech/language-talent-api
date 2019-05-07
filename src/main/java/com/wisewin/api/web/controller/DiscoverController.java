package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.DiscoverJsonBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.DiscoverParam;
import com.wisewin.api.service.DiscoverService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.date.DateUtil;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * Date dcReleasetime; //发布时间
     * String thumbnail; //缩略图url
     * String video; //视频url
     * String type; //类型](课程,新闻,线下活动)
     * Integer priority; //优先级
     * String stick; //置顶[是与否]
     * String show; //是否显示
     * Integer homepage; //页数
     * Integer strip; //每条条数
     */
    @RequestMapping("/queryDiscover")
    public void queryDiscover(HttpServletRequest request, HttpServletResponse response, DiscoverParam param){

    if (param.getHomepage()==null && param.getStrip()==null){
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response,json);
        return;
    }
        List<DiscoverJsonBO> list=discoverService.getqueryDiscover(param.getId(),param.getTitle(),param.getBrowse(), DateUtil.getDate(param.getDcReleasetime()),
                param.getThumbnail(),param.getVideo(),param.getType(),param.getPriority(),param.getStick(),param.getShow(),param.getHomepage(),param.getStrip());

        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response,json);
        return;
    }
    /**
     * 线下活动
     * Integer id; //发现id
     * String title; //发现标题
     * Integer browse; //浏览人数
     * Date dcReleasetime; //发布时间
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

        List<DiscoverBO> list=discoverService.getqueryDiscoveractivity(param.getId(),param.getTitle(),param.getBrowse(),DateUtil.getDate(param.getDcReleasetime()),
                param.getThumbnail(),param.getContent(),param.getType(),param.getLikenum(),param.getParticipation(),DateUtil.getDate(param.getActivitytime()),
                        param.getActivitysite(),param.getPhone(),param.getTicket(),param.getSkip());
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response,json);
        return;

    }
    /**
     * 文章内容显示
     * Integer id; //发现id
     * String title; //发现标题
     * Integer browse; //浏览人数
     * Date dcReleasetime; //发布时间
     * String video; //视频url
     * String content; //内容
     * Integer likenum; //喜欢人数
     *
     */
    @RequestMapping("/queryDiscoveractivitylist")
    public void queryDiscoveractivitylist(HttpServletRequest request,HttpServletResponse response,DiscoverParam param){

        if (param.getId()==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
       // DiscoverBO discoverBO=discoverService.getfindDiscoveractivitybrowse(param.getId());

            List<DiscoverBO> list=discoverService.getqueryDiscoveractivitylist(param.getId(),param.getTitle(),param.getBrowse(),DateUtil.getDate(param.getDcReleasetime()),
                    param.getVideo(),param.getContent(),param.getLikenum());

          //      Integer browse=(Integer) param.getBrowse();
            //    browse++;
                boolean updatediscover=discoverService.getupdateDiscover(param.getId(),param.getBrowse());
                if (updatediscover){
                    String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
                    super.safeJsonPrint(response,json);
                    return;


            }

        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response, json);
        return;
    }
}
