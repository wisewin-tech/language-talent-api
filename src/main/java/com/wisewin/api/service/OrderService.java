package com.wisewin.api.service;

import com.wisewin.api.dao.OrderDAO;
import com.wisewin.api.entity.bo.OrderBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Resource
    private OrderDAO orderDAO;
    @Resource
    private ChapterService  chapterService;
    /**
     * 查询我的交易记录(包括订单,充值)
     * @param map
     * @return
     */
    public List<OrderBO> selectAll(Map<String,Object> map){
        return orderDAO.selectAll(map);
    }
    /**
     * 查询我的交易记录(包括订单,充值)
     * @param id
     * @return
     */
    public OrderBO selectDetails(Integer id,Integer userId){
        return orderDAO.selectDetails(id,userId);
    }

    public String getStatusByCourseId (Integer userId,Integer courseId){
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



}
