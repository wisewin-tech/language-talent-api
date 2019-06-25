
package com.wisewin.api.service;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.wisewin.api.dao.CourseDAO;
import com.wisewin.api.dao.OrderDAO;
import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Resource
    private OrderDAO orderDAO;

    @Resource
    private ChapterService  chapterService;

    @Resource
    CourseDAO courseDAO;
    /**
     * 查询我的交易记录(包括订单,充值)
     * @param map
     * @return
     */
    public List<OrderBO> selectAll(Map<String,Object> map){
        List<OrderBO>  list = orderDAO.listOrderBo(map);
        /*if(list.size()== 0){
            return null;
        }*/
        return list;
    }

    /**
     * 查询我的交易记录(包括订单,充值)
     * @param id
     * @return
     */
    public OrderBO selectDetails(Integer id,Integer userId){
        OrderBO order =  orderDAO.selectDetails(id,userId);
        System.err.println(order);
        if (order == null){
            System.err.println("进入此方法");
            return null;
        }
        return order;
    }

    public Integer getStatusByCourseId (Integer userId,Integer courseId){
        return orderDAO.getStatusByCourseId(userId,courseId);
    }

    /**
     * 查询课时是否能观看
     * @param userId 用户id
     * @param hourId 课时id
     * @return
     */
    public boolean isItWatch(Integer userId,Integer hourId){
        //查询课时所在的课程id
        Integer course=chapterService.getSourceId(hourId);
        //查询订单
        return  orderDAO.queryOrderCount(userId,course)>0;
    }

    /**
     * 查询vid是否存在
     */
    public boolean isVidWahch(String vid){
        return  orderDAO.isVidWahch(vid)>0;
    }

    public boolean isFree(Integer id) {
        return  orderDAO.isFree(id)>0;
    }

    //查询未购买的课程信息
    public List<CourseBO> getBeforeBuyCourseInfo(Integer userId,Integer languageId){
        //语言下的上架课程
        List<CourseBO> allCourseBOList = courseDAO.getCoursesById(languageId);
        //已经购买的课程
        List<CourseBO> buyCourseBOList = orderDAO.getBeforeBuyCourseInfo(userId,languageId);
        for (CourseBO course:allCourseBOList) {
            for (CourseBO buyCourse:buyCourseBOList){
                if(course.getId()==buyCourse.getId()){
                    allCourseBOList.remove(course);
                }
            }
        }
        return allCourseBOList;
    }

    //查询子订单 之前购买并且未过期的总课程的价格
    //根据语言 用户 未过期查询
    public Integer getBeforeBuyCoursePrice(Integer userId, Integer languageId){
        Integer sum=0;
        for (Integer result:orderDAO.getBeforeBuyCoursePrice(userId,languageId)){
            sum+=result;
        }
        return Integer.parseInt(sum.toString());
    }


}