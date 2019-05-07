package com.wisewin.api.service;


import com.wisewin.api.dao.DiscoverDao;
import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.DiscoverJsonBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("DiscoverService")
public class DiscoverService {


    @Resource
    private DiscoverDao discoverDao;


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
    public List<DiscoverJsonBO> getqueryDiscover(Integer id, String title, Integer browse, Date dcReleasetime,String thumbnail,String video,String type,
                                             Integer priority,String stick,String show,Integer homepage,Integer strip){
        DiscoverJsonBO discoverJsonBO=new DiscoverJsonBO(id,title,browse,dcReleasetime,thumbnail,video,type,priority,stick,show,homepage,strip);
            return  discoverDao.queryDiscover(discoverJsonBO);
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
    public List<DiscoverBO> getqueryDiscoveractivity(Integer id,String title,Integer browse,Date dcReleasetime,String thumbnail,String content,
                                                     String type,Integer likenum,Integer participation,Date activitytime,String activitysite,
                                                     String phone,double ticket,String skip){
        DiscoverBO discoverBO=new DiscoverBO(id,title,browse,dcReleasetime,thumbnail,content,type,likenum,participation,activitytime,activitysite,phone,ticket,skip);
        return  discoverDao.queryDiscoveractivity(discoverBO);
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
    public List<DiscoverBO> getqueryDiscoveractivitylist(Integer id,String title,Integer browse,Date dcReleasetime,String video,String content,Integer likenum){

        DiscoverBO discoverBO=new DiscoverBO(id,title,browse,dcReleasetime,video,content,likenum);

        return  discoverDao.queryDiscoveractivitylist(discoverBO);
    }

    /**
     * 发现内容浏览人数查询
     */
    public DiscoverBO getfindDiscoveractivitybrowse(Integer id){
        return  discoverDao.findDiscoveractivitybrowse(id);
    }

    /**
     * 修改浏览人数值
     */
    public  boolean getupdateDiscover(Integer id,Integer browse){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        map.put("browse",browse);
        return  discoverDao.updateDiscover(map)>0;
    }
}
