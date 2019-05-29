
package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.CourseOrderBO;
import com.wisewin.api.entity.bo.LanguageOrderBO;
import com.wisewin.api.entity.bo.OrderBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDAO {
    /**
     * 查询用户订单记录
     * @param map
     * @return
     */
    List<OrderBO> selectAll(Map<String,Object> map);
    OrderBO selectDetails(@Param("id") Integer id, @Param("userId") Integer userId);
    /**
     * 插入订单
     */
    Integer insertOrder(OrderBO order);

    /**
     * 插入预支付订单
     */
    Integer insertPreOrder(OrderBO order);

    /**
     * 修改订单状态为成功
     */
    Integer updOrderStatus(@Param("orderNumber") String orderNumber,@Param("status") String status);

    /**
     * 获取订单信息
     */
    OrderBO getOrderByOrderNumber(String orderNumber);

    Integer getStatusByCourseId(@Param("userId")Integer userId,@Param("courseId")Integer courseId);

    /**
     * 查询用户未过期是否购买
     * @param userId
     * @param course
     * @return
     */
    int queryOrderCount(@Param("userId") Integer userId,@Param("courseId")Integer course);

    /**
     * 查询课程订单
     * @param id
     * @return
     */
    OrderBO courseOrder(String id);

    /**
     * 查询语言订单
     * @param id
     * @return
     */
    OrderBO languageOrder(String id);


    /**
     * 级联查询
     * @param map
     * @return
     */
    List<OrderBO>  listOrderBo(Map<String,Object> map);

    CourseOrderBO selectCouseByOrder(@Param("lc_id")Integer lc_id);

    LanguageOrderBO seleteByLanguageOrder(@Param("lc_id") Integer lc_id);
}
