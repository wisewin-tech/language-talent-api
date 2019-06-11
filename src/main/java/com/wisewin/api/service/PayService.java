
package com.wisewin.api.service;

import com.wisewin.api.common.constants.AliConstants;
import com.wisewin.api.dao.*;
import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.param.OrderParam;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.web.controller.WBAlipayController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PayService {

    static final Logger log = LoggerFactory.getLogger(PayService.class);

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

    @Resource
    CertificateDAO certificateDAO;

    @Resource
    LogService logService;

    //支付成功 充值咖豆  修改订单状态 修改咖豆数量 添加纪录
    public void rechargeKaDou(String orderNumber, Integer currency) {
        logService.serviceStart("PayService.rechargeKaDou",orderNumber,currency);
        //获取到订单信息
        logService.call("orderDAO.getOrderByOrderNumber",orderNumber);
        OrderBO orderBO = orderDAO.getOrderByOrderNumber(orderNumber);
        logService.result(orderBO);
        //订单表状态修改为yes
        logService.call("orderDAO.updOrderStatus",orderNumber, AliConstants.Theorder.getValue());
        orderDAO.updOrderStatus(orderNumber, AliConstants.Theorder.getValue());

        //修改剩余咖豆数量
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currency", currency);
        map.put("id", orderBO.getUserId());
        logService.call("userDAO.updateUserAugment",map);
        userDAO.updateUserAugment(map);

        //记录表添加记录
        RecordBO recordBO = new RecordBO();
        recordBO.setUserId(orderBO.getUserId());
        recordBO.setSource("咖豆");
        recordBO.setStatus("增加");
        recordBO.setSpecificAmount(new Integer(currency));
        recordBO.setDescribe("充值" + currency + "咖豆");
        logService.call("recordDAO.insertUserAction",recordBO);
        recordDAO.insertUserAction(recordBO);
        logService.end("PayService.rechargeKaDou");
    }

    //支付成功 购买课程  修改订单状态 添加子订单
    public void buyCourse(String orderNumber, Integer courseId) throws ParseException {
        logService.serviceStart("PayService.buyCourse",orderNumber,courseId);
        //获取到订单信息
        logService.call("orderDAO.getOrderByOrderNumber",orderNumber);
        OrderBO orderBO = orderDAO.getOrderByOrderNumber(orderNumber);
        logService.result(orderBO);
        //订单表状态修改为yes
        logService.call("orderDAO.updOrderStatus",orderNumber, AliConstants.Theorder.getValue());
        orderDAO.updOrderStatus(orderNumber, AliConstants.Theorder.getValue());
        //查询购买的课程信息
        logService.call("courseDAO.getCourseById",courseId);
        CourseBO courseBO = courseDAO.getCourseById(courseId);
        logService.result(courseBO);

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
        logService.call("orderCoursesDAO.addCourses",orderCoursesBOList);
        orderCoursesDAO.addCourses(orderCoursesBOList);

        //添加证书
        CertificateBO certificateBO=new CertificateBO();
        certificateBO.setUserId(orderBO.getUserId());
        certificateBO.setCourseId(courseId);
        List<CertificateBO> certificateBOList=new ArrayList<CertificateBO>();
        certificateBOList.add(certificateBO);
        logService.call("certificateDAO.addCertificate",certificateBOList);
        certificateDAO.addCertificate(certificateBOList);
        logService.end("PayService.buyCourse");
    }

    //支付成功 购买语言 添加多个子订单
    public void buyLanguage(String orderNumber, Integer languageId) throws ParseException {
        logService.serviceStart("PayService.buyLanguage",orderNumber,languageId);
        //获取到订单信息
        logService.call("orderDAO.getOrderByOrderNumber",orderNumber);
        OrderBO orderBO = orderDAO.getOrderByOrderNumber(orderNumber);
        logService.result(orderBO);
        //订单表状态修改为yes
        logService.call("orderDAO.updOrderStatus",orderNumber, AliConstants.Theorder.getValue());
        orderDAO.updOrderStatus(orderNumber, AliConstants.Theorder.getValue());

        //查询购买的课程信息 因为是购买语言，课程可能有多个
        logService.call("courseDAO.getCoursesById",languageId);
        List<CourseBO> courseBOList = courseDAO.getCoursesById(languageId);
        logService.result(courseBOList);
        //证书
        List<CertificateBO> certificateBOList=new ArrayList<CertificateBO>();

        //添加 订单 子订单表  添加证书
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

            //实例化证书
            CertificateBO certificateBO=new CertificateBO();
            certificateBO.setUserId(orderBO.getUserId());
            certificateBO.setCourseId(courseBO.getId());
            certificateBOList.add(certificateBO);
        }
        logService.call("orderCoursesDAO.addCourses",orderCoursesBOList);
        orderCoursesDAO.addCourses(orderCoursesBOList);
        //添加证书
        logService.call("certificateDAO.addCertificate",certificateBOList);
        certificateDAO.addCertificate(certificateBOList);
        logService.end("PayService.buyLanguage");
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
        logService.serviceStart("PayService.prepaid",orderParam);
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
        logService.call("orderDAO.insertPreOrder",orderBO);
        orderDAO.insertPreOrder(orderBO);
        logService.end("PayService.prepaid");
    }
}