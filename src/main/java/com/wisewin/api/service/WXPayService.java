package com.wisewin.api.service;

import com.wisewin.api.common.constants.AliConstants;
import com.wisewin.api.dao.*;
import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.param.OrderParam;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.wxUtil.WXMsg;
import com.wisewin.api.util.wxUtil.WXPayRequest;
import com.wisewin.api.util.wxUtil.WXPayUtil;
import com.wisewin.api.util.wxUtil.config.WXConfig;
import com.wisewin.api.util.wxUtil.config.WXRequestConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
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

    @Resource
    LanguageDAO languageDAO;

    @Resource
    KeyValDAO keyValDAO;

    //预支付下单
    //给安卓返回预支付信息调用支付
    //插入未支付订单
    public Map<String, String> getUnifiedOrder(OrderParam orderParam) throws Exception {
        //1.获取请求参数
        Map<String, String> map = getWXPayParams(orderParam);

        //2.第一次签名
        String mapStr = WXPayUtil.generateSignedXml(map, WXConfig.KEY);

        //3.发送请求 获取到预支付信息  partnerid
        String result = getCodeUrl(mapStr);

        //预支付订单信息Map
        Map<String, String> resultMap = WXPayUtil.xmlToMap(result);

        //4.初始化二次签名信息 用第一次请求拿到的信息中的prepayid
        Map<String,String> twoMap=new HashMap<String, String>();
        twoMap.put("appid",resultMap.get("appid"));
        twoMap.put("partnerid",resultMap.get("mch_id"));
        twoMap.put("prepayid",resultMap.get("prepay_id"));//第一次发送请求拿到的预支付id
        twoMap.put("noncestr",resultMap.get("nonce_str"));
        twoMap.put("timestamp",WXPayUtil.getCurrentTimestamp()+"");//时间戳
        twoMap.put("package","Sign=WXPay");

        //6.第二次签名 把这个签名给安卓拉起支付请求
        String twoMapStr = WXPayUtil.generateSignedXml(twoMap, WXConfig.KEY);
        //给前端调用的Map
        twoMap=WXPayUtil.xmlToMap(twoMapStr);
        System.out.println(twoMapStr);

        //存入自己的数据库
        if (twoMap != null && !twoMap.isEmpty()) {
            prepaid(orderParam);
        }

        return twoMap;
    }

    //请求统一下单
    public String getCodeUrl(String mapStr) throws Exception {
        WXRequestConfig wxPayConfig=new WXRequestConfig();
        WXPayRequest wxPayRequest=new WXPayRequest(wxPayConfig);
        //方法形参中需要带个uuid 不清楚干啥的随机生成了
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        String resultXml = wxPayRequest.requestWithoutCert(WXConfig.PLACE_AN_ORDERAPI,uuidStr,mapStr,false);
        return resultXml;
    }

    //支付成功回调
    public Map<String, String> getOrderResult(HttpServletRequest request,String productType,Map<String,String> resultMap) throws Exception {
        //接受微信回调参数
//        InputStream inStream = request.getInputStream();
//        //转换为map
//        Map<String, String> resultMap = inStreamToMap(inStream);
        //处理业务逻辑
        String return_code = resultMap.get("return_code");//状态
        String out_trade_no = resultMap.get("out_trade_no");//商户订单号
        String trade_state = resultMap.get("trade_state");//交易状态
        if (return_code.equals("SUCCESS")) {//交易标识
            if (out_trade_no != null) {//商户订单号
                if (trade_state.equals("SUCCESS")) {//支付成功
                    if (productType.equals("咖豆")){
                        //调用充值咖豆的方法
                        rechargeKaDou(resultMap.get("out_trade_no"),new Integer(resultMap.get("attach")));
                    }else if(productType.equals("课程")){
                        //购买课程
                        buyCourse(resultMap.get("out_trade_no"),new Integer(  resultMap.get("attach")));
                    }else if(productType.equals("课程")) {
                        //购买语言
                        buyLanguage(resultMap.get("out_trade_no"),new Integer(  resultMap.get("attach")));
                    }

                }
            }
        }
                return resultMap;
    }

    //插入预支付订单
    public void prepaid(OrderParam orderParam){
        //实例化订单对象 完成插入订单操作
        OrderBO orderBO = new OrderBO();
        orderBO.setUserId(orderParam.getUserId());
        orderBO.setPrice(orderParam.getPrice());
        orderBO.setOrderNumber(orderParam.getOrderNumber());
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

    //支付成功 充值咖豆  修改订单状态 修改咖豆数量 添加纪录
    public void rechargeKaDou(String orderNumber,Integer currency){
        //获取到订单信息
        OrderBO orderBO = orderDAO.getOrderByOrderNumber(orderNumber);

        //订单表状态修改为yes
        orderDAO.updOrderStatus(orderNumber, AliConstants.Theorder.getValue());

        //修改剩余咖豆数量
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currency",currency);
        map.put("id",orderBO.getUserId());
        userDAO.updateUserAugment(map);

        //记录表添加记录
        RecordBO recordBO=new RecordBO();
        recordBO.setUserId(orderBO.getUserId());
        recordBO.setSource("咖豆");
        recordBO.setStatus("增加");
        recordBO.setSpecificAmount(new Integer(currency));
        recordBO.setDescribe("充值"+currency+"咖豆");
        recordDAO.insertUserAction(recordBO);

    }

    //支付成功 购买课程  修改订单状态 添加子订单
    public void buyCourse(String orderNumber,Integer courseId) throws ParseException {
        //获取到订单信息
        OrderBO orderBO = orderDAO.getOrderByOrderNumber(orderNumber);

        //订单表状态修改为yes
        orderDAO.updOrderStatus(orderNumber, AliConstants.Theorder.getValue());

        //查询购买的课程信息
        CourseBO courseBO=courseDAO.getCourseById(courseId);

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

    //支付成功 购买语言 添加多个子订单
    public void buyLanguage(String orderNumber,Integer languageId) throws ParseException {
        //获取到订单信息
        OrderBO orderBO = orderDAO.getOrderByOrderNumber(orderNumber);

        //订单表状态修改为yes
        orderDAO.updOrderStatus(orderNumber, AliConstants.Theorder.getValue());

        //查询购买的课程信息 因为是购买语言，课程可能有多个
        List<CourseBO> courseBOList=courseDAO.getCoursesById(languageId);

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

    //转换金额 有 1.00 转为 100分
    private static String totalFee(BigDecimal  price){

        BigDecimal setScale = price.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        System.out.println(setScale);
        String str= setScale.multiply(new BigDecimal("100")).toString();
        BigDecimal b=   new BigDecimal(str.substring(0,str.length()-3));

        return  b.toString();
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

    //生成请求参数的map
    public Map<String,String> getWXPayParams(OrderParam orderParam) throws ParseException {
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId() + "";
        orderParam.setOrderNumber(orderNumber);

        //获取请求参数
        Map<String, String> map = WXConfig.toMapJSAPI();

        //传入剩余请求参数
        map.put("out_trade_no",orderNumber);//订单号
        //判断购买类型 添加后续请求参数
        if(orderParam.getProductType().equals("咖豆")){
            //map.put("total_fee",totalFee(orderParam.getPrice()));//订单价格
            map.put("total_fee",totalFee(new BigDecimal("0.01")));
            //自定义请求参数 购买咖豆的数量
            map.put("attach", getKaDou(new Integer(orderParam.getPrice().intValue()))+"");//！！！！！！！！！！！
            //回调地址
            map.put("notify_url",WXConfig.NOTIFY_URL_CURRENCY);
        }else if(orderParam.getProductType().equals("课程")){
            //自定义请求参数 课程id
            map.put("attach", orderParam.getCourseId() + "");
            //要购买的课程
            CourseBO courseBO = courseDAO.getCourseById(orderParam.getCourseId());
            //当前时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            //判断是否是优惠时间
            boolean bool=isEffectiveDate(df.parse((df.format(new Date()))),courseBO.getDiscountStartTime(),courseBO.getDiscountEndTime());
            //价格
            if(bool){
                //自定义请求参数 价格
               // map.put("total_fee",totalFee(getMoney(courseBO.getDiscountPrice())));
                orderParam.setPrice(getMoney(courseBO.getDiscountPrice()));
                 map.put("total_fee",totalFee(new BigDecimal("0.01")));
            }else{
                //自定义请求参数 价格
                //map.put("total_fee",totalFee(getMoney(courseBO.getPrice())));
                orderParam.setPrice(getMoney(courseBO.getPrice()));
                map.put("total_fee",totalFee(new BigDecimal("0.01")));
            }

            //回调地址
            map.put("notify_url",WXConfig.NOTIFY_URL_COURSE);
        }else if(orderParam.getProductType().equals("语言")) {
            //自定义请求参数 语言id
            map.put("attach", orderParam.getLanguageId() + "");
            //要购买的语言
            LanguageBO languageBO = languageDAO.selectLanguageG(orderParam.getLanguageId()+"");
            //当前时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            //判断是否是优惠时间
            boolean bool=isEffectiveDate(df.parse(df.format(new Date())),languageBO.getDiscountStartTime(),languageBO.getDiscountEndTime());
            //价格
            if(bool){
                //自定义请求参数 价格
                //map.put("total_fee",totalFee(getMoney(languageBO.getLanguageDiscountPrice())));
                orderParam.setPrice(getMoney(languageBO.getLanguageDiscountPrice()));
                map.put("total_fee",totalFee(new BigDecimal("0.01")));
            }else{
                //自定义请求参数 价格
                //map.put("total_fee",totalFee(getMoney(languageBO.getLanguagePrice())));
                orderParam.setPrice(getMoney(languageBO.getLanguagePrice()));
                map.put("total_fee",totalFee(new BigDecimal("0.01")));
            }
            //回调地址
            map.put("notify_url",WXConfig.NOTIFY_URL_LANGUAGE);
        }
        return map;

    }

    //获取人民币对等的咖豆
    public Integer getKaDou(Integer price){
        //String proportion = keyValDAO.selectKey("top-up proportion");
        String proportion = "1:1";
        Integer jinE=new Integer(proportion.substring(0,proportion.indexOf(":")));
        Integer kaD=new Integer(proportion.substring(proportion.indexOf(":")+1,proportion.length()));
        return price/jinE*kaD;
    }

    //获取咖豆对等的人民币
    public BigDecimal getMoney(Integer kaDou){
        //String proportion = keyValDAO.selectKey("top-up proportion");
        String proportion = "1:1";
        Integer jinE=new Integer(proportion.substring(0,proportion.indexOf(":")));
        Integer kaD=new Integer(proportion.substring(proportion.indexOf(":")+1,proportion.length()));
        return new BigDecimal(kaDou/kaD*jinE);
    }
}


