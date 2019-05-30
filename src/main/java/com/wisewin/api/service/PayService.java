package com.wisewin.api.service;

import com.wisewin.api.common.constants.AliConstants;
import com.wisewin.api.dao.*;
import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.OrderCoursesBO;
import com.wisewin.api.entity.bo.RecordBO;
import com.wisewin.api.entity.param.OrderParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PayService {
    @Resource
    OrderDAO orderDAO;

    @Resource
    UserDAO userDAO;

    @Resource
    RecordDAO recordDAO;

    @Resource
    OrderCoursesDAO orderCoursesDAO;

    @Resource
    CourseDAO courseDAO;

    //支付成功 充值咖豆  修改订单状态 修改咖豆数量 添加纪录
    public void rechargeKaDou(String orderNumber, Integer currency) {
        //获取到订单信息
        OrderBO orderBO = orderDAO.getOrderByOrderNumber(orderNumber);

        //订单表状态修改为yes
        orderDAO.updOrderStatus(orderNumber, AliConstants.Theorder.getValue());

        //修改剩余咖豆数量
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currency", currency);
        map.put("id", orderBO.getUserId());
        userDAO.updateUserAugment(map);

        //记录表添加记录
        RecordBO recordBO = new RecordBO();
        recordBO.setUserId(orderBO.getUserId());
        recordBO.setSource("咖豆");
        recordBO.setStatus("增加");
        recordBO.setSpecificAmount(new Integer(currency));
        recordBO.setDescribe("充值" + currency + "咖豆");
        recordDAO.insertUserAction(recordBO);

    }

    //支付成功 购买课程  修改订单状态 添加子订单
    public void buyCourse(String orderNumber, Integer courseId) throws ParseException {
        //获取到订单信息
        OrderBO orderBO = orderDAO.getOrderByOrderNumber(orderNumber);
        //订单表状态修改为yes
        orderDAO.updOrderStatus(orderNumber, AliConstants.Theorder.getValue());
        //查询购买的课程信息
        CourseBO courseBO = courseDAO.getCourseById(courseId);

        //实例化子订单信息
        OrderCoursesBO orderCoursesBO = new OrderCoursesBO();
        orderCoursesBO.setUserId(orderBO.getUserId());
        orderCoursesBO.setOrderId(orderBO.getId());
        orderCoursesBO.setCoursesId(courseBO.getId());
        orderCoursesBO.setCoursesName(courseBO.getCourseName());

        //课程有效期
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, courseBO.getCourseValidityPeriod());
        orderCoursesBO.setCourseValidityPeriod(sf.parse(sf.format(c.getTime())));

        //添加 订单 子订单表
        List<OrderCoursesBO> orderCoursesBOList = new ArrayList<OrderCoursesBO>();
        orderCoursesBOList.add(orderCoursesBO);
        orderCoursesDAO.addCourses(orderCoursesBOList);
    }

    //支付成功 购买语言 添加多个子订单
    public void buyLanguage(String orderNumber, Integer languageId) throws ParseException {

        //获取到订单信息
        OrderBO orderBO = orderDAO.getOrderByOrderNumber(orderNumber);

        //订单表状态修改为yes
        orderDAO.updOrderStatus(orderNumber, AliConstants.Theorder.getValue());

        //查询购买的课程信息 因为是购买语言，课程可能有多个
        List<CourseBO> courseBOList = courseDAO.getCoursesById(languageId);

        //添加 订单 子订单表
        List<OrderCoursesBO> orderCoursesBOList = new ArrayList<OrderCoursesBO>();
        for (CourseBO courseBO : courseBOList) {
            //实例化子订单信息
            OrderCoursesBO orderCoursesBO = new OrderCoursesBO();
            orderCoursesBO.setUserId(orderBO.getUserId());
            orderCoursesBO.setOrderId(orderBO.getId());
            orderCoursesBO.setCoursesId(courseBO.getId());
            orderCoursesBO.setCoursesName(courseBO.getCourseName());
            //课程有效期
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, courseBO.getCourseValidityPeriod());
            orderCoursesBO.setCourseValidityPeriod(sf.parse(sf.format(c.getTime())));
            orderCoursesBOList.add(orderCoursesBO);
        }

        orderCoursesDAO.addCourses(orderCoursesBOList);
    }

    //获取人民币对等的咖豆
    public Integer getKaDou(Integer price) {
        //String proportion = keyValDAO.selectKey("top-up proportion");
        String proportion = "1:1";
        Integer jinE = new Integer(proportion.substring(0, proportion.indexOf(":")));
        Integer kaD = new Integer(proportion.substring(proportion.indexOf(":") + 1, proportion.length()));
        return price / jinE * kaD;
    }

    //获取咖豆对等的人民币
    public BigDecimal getMoney(Integer kaDou) {
        //String proportion = keyValDAO.selectKey("top-up proportion");
        String proportion = "1:1";
        Integer jinE = new Integer(proportion.substring(0, proportion.indexOf(":")));
        Integer kaD = new Integer(proportion.substring(proportion.indexOf(":") + 1, proportion.length()));
        return new BigDecimal(kaDou / kaD * jinE);
    }

    //判断是否是优惠时间
    public boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    //插入预支付订单
    public void prepaid(OrderParam orderParam) {
        //实例化订单对象 完成插入订单操作
        OrderBO orderBO = new OrderBO();
        orderBO.setUserId(orderParam.getUserId());
        orderBO.setPrice(orderParam.getPrice());
        orderBO.setOrderNumber(orderParam.getOrderNumber());

        if (orderParam.getProductType().equals("currency")) {
            if("zfb".equals(orderParam.getPayment())){
                orderBO.setOrderType("支付宝充值");
            }else{
                orderBO.setOrderType("微信充值");
            }
        } else {
            if("zfb".equals(orderParam.getPayment())){
                orderBO.setOrderType("支付宝购买");
            }else{
                orderBO.setOrderType("微信购买");
            }

            orderBO.setType(orderParam.getProductType());

            if(orderParam.getProductType().equals("curriculum")){
                orderBO.setLcId(orderParam.getCourseId());
            }
            if(orderParam.getProductType().equals("language")){
                orderBO.setLcId(orderParam.getLanguageId());
            }


        }
        orderBO.setProductName(orderParam.getProductName());
        //未支付
        orderBO.setStatus(AliConstants.Didnotpay.getValue());
        //插入数据库 订单信息
        orderDAO.insertPreOrder(orderBO);
    }
}
