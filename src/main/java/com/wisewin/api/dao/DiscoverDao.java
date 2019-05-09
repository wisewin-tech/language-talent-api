package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.DiscoverBO;
import com.wisewin.api.entity.bo.DiscoverJsonBO;
import com.wisewin.api.entity.param.DiscoverParam;

import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Map;

/**
 * 发现
 */
public interface DiscoverDao {

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
     * * Integer homepage; //页数
     * Integer strip; //每条条数
     */
    List<DiscoverJsonBO> queryDiscover(DiscoverParam param);

    /**
     * 显示线下活动全部
     */
    List<DiscoverJsonBO> queryDiscovertype(DiscoverParam param);

    /**
     * 线下活动
     * Integer id; //发现id
     * String title; //发现标题
     * Integer browse; //浏览人数
     *Date createTime; //发布时间
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
    DiscoverBO queryDiscoveractivity(DiscoverBO discoverBO);

    /**
     * 首页分页条件查询总数
     */
    int findcoun(int countnum);


    /**
     * 查询浏览人数
     */
    DiscoverBO findDiscoveractivitybrowse(Integer id);

    /**
     * 修改浏览人数
     */
    Integer updateDiscover(Map<String,Object> map);

    /**
     * 修改喜欢值
     * 发现 Integer id
     */
    Integer updatelikenumDiscover(Map<String,Object> map);

    /**
     * 查找喜欢，人数方法
     */
    DiscoverBO findDiscoverlikenum(Integer id);




}
