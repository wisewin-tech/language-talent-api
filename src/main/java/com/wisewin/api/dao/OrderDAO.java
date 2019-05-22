
package com.wisewin.api.dao;

import com.wisewin.api.common.constants.AliConstants;
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
    Integer updOrderStatus(String orderNumber,String status);

    /**
     * 获取订单信息
     */
    OrderBO getOrderByOrderNumber(String orderNumber);

    String getStatusByCourseId(Integer userId,Integer courseId);


}
