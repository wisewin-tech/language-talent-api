package com.wisewin.api.service;


import com.wisewin.api.dao.DiscoverDao;
import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.DiscoverJsonBO;
import com.wisewin.api.entity.param.DiscoverParam;
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
     * Date createTime; //发布时间
     * String thumbnail; //缩略图url
     * String video; //视频url
     * String type; //类型](课程,新闻,线下活动)
     * Integer priority; //优先级
     * String stick; //置顶[是与否]
     * String show; //是否显示
     * String way; //展示方式
     *Integer pageOffset; //页数
      Integer pageSize; //每条条数
     */
    public List<DiscoverJsonBO> getqueryDiscover(DiscoverParam param){

            return  discoverDao.queryDiscover(param);
    }

    /**
     * 首页线下活动
     * * Integer id; //发现id
     * String title; //发现标题
     * Integer browse; //浏览人数
     * Date createTime; //发布时间
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
     * String way; //展示方式
     */
    public List<DiscoverJsonBO> getqueryDiscovertype(DiscoverParam param){

        return  discoverDao.queryDiscovertype(param);
    }


    /**
     * 线下活动
     * Integer id; //发现id
     * String title; //发现标题
     * Integer browse; //浏览人数
     * Date createTime; //发布时间
     * String thumbnail; //缩略图url
     * 视频
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
    public DiscoverBO getqueryDiscoveractivity(Integer id,String title,Integer browse,Date createTime,String thumbnail,String video,String content,
                                                     String type,Integer likenum,Integer participation,Date activitytime,String activitysite,
                                                     String phone,double ticket,String skip,String videoImg){
        DiscoverBO discoverBO=new DiscoverBO(id,title,browse,createTime,thumbnail,video,content,type,likenum,participation,activitytime,activitysite,phone,ticket,skip,videoImg);
        return  discoverDao.queryDiscoveractivity(discoverBO);
    }

    /**
     * 首页分页条件查询总数
     */
    public int getfindcoun(int countnum){
        return  discoverDao.findcoun(countnum);
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

    /**
     * 修改喜欢值
     * 发现 Integer id
     * Integer likenum; 喜欢
     */
    public boolean getupdatelikenumDiscover(Integer id,Integer likenum){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        map.put("likenum",likenum);
        return  discoverDao.updatelikenumDiscover(map)>0;
    }


    /**
     * 查找喜欢
     */
    public DiscoverBO getfindDiscoverlikenum(Integer id){

        return  discoverDao.findDiscoverlikenum(id);
    }
}
