package com.wisewin.api.service;

import com.wisewin.api.common.constants.AliConstants;
import com.wisewin.api.dao.*;
import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.OrderCoursesBO;
import com.wisewin.api.entity.bo.RecordBO;
import com.wisewin.api.entity.param.OrderParam;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.wxUtil.WXMsg;
import com.wisewin.api.util.wxUtil.WXPayUtil;
import com.wisewin.api.util.wxUtil.config.WXConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WXPayService {

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

    //预支付下单
    public Map<String, String> getUnifiedOrder(OrderParam orderParam) throws Exception {
        //1.实例化对象
        WXMsg wxMsg = new WXMsg();

        //2.生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId() + "";

        //3.获取请求参数
        Map<String, String> map = wxMsg.getWXPayParams(orderNumber, orderParam.getPrice());

        //判断购买类型 添加后续请求参数
        if(orderParam.getProductType().equals("咖豆")){
            //自定义请求参数 购买咖豆的数量
            map.put("attach", orderParam.getCurrency() + "");
            //回调地址
            map.put("notify_url",WXConfig.NOTIFY_URL_CURRENCY);
        }else if(orderParam.getProductType().equals("课程")){
            //自定义请求参数 购买咖豆的数量
            map.put("attach", orderParam.getCourseId() + "");
            //回调地址
            map.put("notify_url",WXConfig.NOTIFY_URL_COURSE);
        }else if(orderParam.getProductType().equals("语言")) {
            //自定义请求参数 购买咖豆的数量
            map.put("attach", orderParam.getLanguageId() + "");
            //回调地址
            map.put("notify_url",WXConfig.NOTIFY_URL_LANGUAGE);
        }

        //3.第一次签名
        String mapStr = WXPayUtil.generateSignedXml(map, WXConfig.KEY);

        //4.发送请求 获取到预支付订单信息
        String result = wxMsg.getCodeUrl(mapStr);
        System.out.println(result);

        //预支付订单信息Map
        Map<String, String> resultMap = WXPayUtil.xmlToMap(result);

        //5.用第一次请求拿到的信息中的prepayid 二次签名
        Map<String,String> twoMap=new HashMap<String, String>();
        twoMap.put("appid",resultMap.get("appid"));
        twoMap.put("partnerid",resultMap.get("mch_id"));
        twoMap.put("prepayid",resultMap.get("prepay_id"));//第一次发送请求拿到的预支付id
        twoMap.put("noncestr",resultMap.get("nonce_str"));
        twoMap.put("timestamp",WXPayUtil.getCurrentTimestamp()+"");//时间戳
        twoMap.put("package","Sign=WXPay");

        //6.第二次签名 把这个签名给安卓拉起支付请求
        String twoMapStr = WXPayUtil.generateSignedXml(twoMap, WXConfig.KEY);
        twoMap=WXPayUtil.xmlToMap(twoMapStr);
        System.out.println(twoMap);

        //存入自己的数据库
        if (twoMap != null && !twoMap.isEmpty()) {
            //实例化订单对象 完成插入订单操作
            OrderBO orderBO = new OrderBO();
            orderBO.setUserId(orderParam.getUserId());
            orderBO.setPrice(orderParam.getPrice());
            orderBO.setOrderNumber(orderNumber);
            if(orderParam.getProductType().equals("咖豆")){
                orderBO.setOrderType("充值");
            }else{
                orderBO.setOrderType("购买");
            }
            orderBO.setProductName(orderParam.getProductName());
            //未支付
            orderBO.setStatus(AliConstants.Didnotpay.getValue());

            //插入数据库 订单信息
            orderDAO.insertPreOrder(orderBO);
        }

        return twoMap;
    }

    //充值咖豆
    public Map<String, String> getOrderResult(HttpServletRequest request) throws Exception {
        //接受微信回调参数
        InputStream inStream = request.getInputStream();
        //转换为map
        Map<String, String> resultMap = inStreamToMap(inStream);

        //测试
        System.out.println(resultMap.get("return_code"));
        System.out.println(resultMap.get("out_trade_no"));
        System.out.println(resultMap.get("trade_state"));
        System.out.println(resultMap.get("attach"));
        //处理业务逻辑
        String return_code = resultMap.get("return_code");//状态
        String out_trade_no = resultMap.get("out_trade_no");//商户订单号
        String trade_state = resultMap.get("trade_state");//交易状态
        if (return_code.equals("SUCCESS")) {//交易标识
            if (out_trade_no != null) {//商户订单号
                if (trade_state.equals("SUCCESS")) {//支付成功
                    //获取到订单信息
                    OrderBO orderBO = orderDAO.getOrderByOrderNumber(resultMap.get("out_trade_no"));

                    //订单表状态修改为yes
                    orderDAO.updOrderStatus(out_trade_no, AliConstants.Theorder.getValue());

                    //修改剩余咖豆数量
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("currency", resultMap.get("attach"));
                    map.put("id",orderBO.getUserId());
                    userDAO.updateUserAugment(map);

                    //记录表添加记录
                    RecordBO recordBO=new RecordBO();
                    recordBO.setUserId(orderBO.getUserId());
                    recordBO.setSource("咖豆");
                    recordBO.setStatus("增加");
                    recordBO.setSpecificAmount(new Integer(resultMap.get("attach")));
                    recordBO.setDescribe("充值"+resultMap.get("attach")+"咖豆");
                    recordDAO.insertUserAction(recordBO);

                }
            }
        }
                return resultMap;
    }

    //购买课程
    public Map<String,String> courseOrderResult(HttpServletRequest request) throws Exception{
        //接受微信回调参数
        InputStream inStream = request.getInputStream();
        //转换为map
        Map<String, String> resultMap = inStreamToMap(inStream);
        //处理业务逻辑
        String return_code = resultMap.get("return_code");//状态
        String out_trade_no = resultMap.get("out_trade_no");//商户订单号
        String trade_state = resultMap.get("trade_state");//交易状态
        if (return_code.equals("SUCCESS")) {//交易标识
            if (out_trade_no != null) {//商户订单号
                if (trade_state.equals("SUCCESS")) {//支付成功
                    //获取到订单信息
                    OrderBO orderBO = orderDAO.getOrderByOrderNumber(resultMap.get("out_trade_no"));

                    //订单表状态修改为yes
                    orderDAO.updOrderStatus(out_trade_no, AliConstants.Theorder.getValue());

                    //查询购买的课程信息
                    CourseBO courseBO=courseDAO.getCurseNameById(new Integer(resultMap.get("attach")));

                    //实例化子订单信息
                    OrderCoursesBO orderCoursesBO=new OrderCoursesBO();
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
                    List<OrderCoursesBO> orderCoursesBOList=new ArrayList<OrderCoursesBO>();
                    orderCoursesBOList.add(orderCoursesBO);
                    orderCoursesDAO.addCourses(orderCoursesBOList);

                }
            }
        }
        return resultMap;
    }

    //购买语言
    public Map<String,String> languageOrderResult(HttpServletRequest request) throws Exception{
        //接受微信回调参数
        InputStream inStream = request.getInputStream();
        //转换为map
        Map<String, String> resultMap = inStreamToMap(inStream);
        //处理业务逻辑
        String return_code = resultMap.get("return_code");//状态
        String out_trade_no = resultMap.get("out_trade_no");//商户订单号
        String trade_state = resultMap.get("trade_state");//交易状态
        if (return_code.equals("SUCCESS")) {//交易标识
            if (out_trade_no != null) {//商户订单号
                if (trade_state.equals("SUCCESS")) {//支付成功
                    //获取到订单信息
                    OrderBO orderBO = orderDAO.getOrderByOrderNumber(resultMap.get("out_trade_no"));

                    //订单表状态修改为yes
                    orderDAO.updOrderStatus(out_trade_no, AliConstants.Theorder.getValue());

                    //查询购买的课程信息 因为是购买语言，课程可能有多个
                    List<CourseBO> courseBOList=courseDAO.getCourseById(new Integer(resultMap.get("attach")));

                    //添加 订单 子订单表
                    List<OrderCoursesBO> orderCoursesBOList=new ArrayList<OrderCoursesBO>();
                    for (CourseBO courseBO:courseBOList) {
                        //实例化子订单信息
                        OrderCoursesBO orderCoursesBO=new OrderCoursesBO();
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
            }
        }
        return resultMap;
    }



    //将InputStream转换为Map
    public Map<String, String> inStreamToMap(InputStream inStream) throws Exception {
        int _buffer_size = 1024;
        Map<String, String> resultMap = new HashMap<String, String>();
        if (inStream != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] tempBytes = new byte[_buffer_size];
            int count = -1;
            while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                outStream.write(tempBytes, 0, count);
            }
            tempBytes = null;
            outStream.flush();
            //将流转换成字符串
            String result = new String(outStream.toByteArray(), "UTF-8");
            //将字符串解析成MAP
            resultMap = WXPayUtil.xmlToMap(result);

        }
        return resultMap;
    }
}
