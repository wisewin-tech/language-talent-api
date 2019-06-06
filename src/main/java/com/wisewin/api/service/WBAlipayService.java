package com.wisewin.api.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.wisewin.api.dao.CourseDAO;
import com.wisewin.api.dao.LanguageDAO;
import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.LanguageBO;
import com.wisewin.api.entity.dto.AlipayBTO;
import com.wisewin.api.entity.param.OrderParam;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.AlipayConfig;
import com.wisewin.api.util.IDBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 王彬 on 2019/5/28.
 */
@Service
@Transactional
public class WBAlipayService {

    @Resource
    private CourseDAO courseDAO;

    @Resource
    private LanguageDAO languageDAO;

    @Resource
    private PayService payService;

    @Resource
    private LogService logService;


     AlipayClient   client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID,
                AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);

    static final Logger log = LoggerFactory.getLogger(WBAlipayService.class);

    // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，
    // 调用RSA签名方式
    AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
    // 封装请求支付信息
    AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

    public String currencyPay(OrderParam orderParam){
        logService.serviceStart("com.wisewin.api.service.WBAlipayService.currencyPay"+orderParam.toString());
        //生成订单号
        IDBuilder idBuilder  =  new IDBuilder(10,10);
        String number  = idBuilder.nextId()+"";
        orderParam.setOrderNumber(number);
        try {
            //DecimalFormat类型金额保留两位精度并转String
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            String price = df.format(orderParam.getPrice());
            model.setSubject("currency");
            model.setOutTradeNo(number);
            log.info(price+"金额");
            System.err.println(price+"金额");
            model.setTotalAmount(price);
            model.setProductCode("QUICK_MSECURITY_PAY");
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AlipayConfig.fy_url);
            // 调用SDK生成表单
            AlipayTradeAppPayResponse ali_response = client.sdkExecute(ali_request);
            if (ali_response.isSuccess()) {
                //插入课程预支付订单
                logService.call("com.wisewin.api.service.PayService.prepaid",orderParam.toString());
                payService.prepaid(orderParam);
                // 获取到getBody直接给app,用这个东西去调起支付宝
                //System.err.println(ali_response.getBody());
                logService.end("com.wisewin.api.service.WBAlipayService.currencyPay",ali_response.getBody().toString());
                return ali_response.getBody();
              /*  cotroller.safeJsonPrint(response,ali_response.getBody());
                return;*/
            } else {
                log.info("error");
                throw new AlipayApiException("调用SDK生成表单失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        logService.end("com.wisewin.api.service.WBAlipayService.currencyPay","null");
        return null;
    }


    /**
     * 购买语言
     * @param orderParam
     * @return
     */
    public  String languagePay(OrderParam orderParam){
        //生成订单号
        IDBuilder idBuilder  =  new IDBuilder(10,10);
        String number  = idBuilder.nextId()+"";
        orderParam.setOrderNumber(number);
        try {
            //DecimalFormat类型金额保留两位精度并转String
            //DecimalFormat df = new DecimalFormat("0.00");
            //df.setRoundingMode(RoundingMode.HALF_UP);

            //要购买的语言
            LanguageBO languageBO = languageDAO.selectLanguageG(orderParam.getLanguageId() + "");
            Integer price;
            //判断时间
            boolean fag = belongCalendar(new Date(),languageBO.getDiscountStartTime(),languageBO.getDiscountEndTime());
            if (fag) {
                //获取优惠语言价格
                 price = languageBO.getLanguageDiscountPrice();
                 System.err.println(price);
            }else{
                 price = languageBO.getLanguagePrice();

            }
            model.setSubject("language");
            model.setOutTradeNo(number);
            System.err.println(price+".00");
            model.setTotalAmount(price+".00");
            //model.setTotalAmount("0.01");
            model.setProductCode("QUICK_MSECURITY_PAY");
            String languageId = orderParam.getLanguageId()+"";
            try {
                String passback_params =java.net.URLEncoder.encode(languageId,"UTF-8");
                //传入课程id
                model.setPassbackParams(passback_params);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AlipayConfig.fy_url);
            // 调用SDK生成表单
            AlipayTradeAppPayResponse ali_response = client.sdkExecute(ali_request);
            orderParam.setPrice(new BigDecimal(price));
            if (ali_response.isSuccess()) {
                //插入充值预支付订单
                System.err.println(orderParam);
                payService.prepaid(orderParam);
                // 获取到getBody直接给app,用这个东西去调起支付宝
                System.out.println(ali_response.getBody());
                //return ali_response.getBody();
                return ali_response.getBody();
            } else {
                log.debug("调用SDK生成表单失败");
                throw new AlipayApiException("调用SDK生成表单失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 购买课程
     * @param orderParam
     * @return
     */
    public String curriculumPay(OrderParam orderParam){
        //获取购买课程对象
        CourseBO courseBO = courseDAO.getCourseById(orderParam.getCourseId());
        //生成订单号
        IDBuilder idBuilder  =  new IDBuilder(10,10);
        String number  = idBuilder.nextId()+"";
        orderParam.setOrderNumber(number);
        try {
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，
            // 调用RSA签名方式
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            // 封装请求支付信息
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //DecimalFormat类型金额保留两位精度并转String
           // DecimalFormat df = new DecimalFormat("0.00");
            //df.setRoundingMode(RoundingMode.HALF_UP);
            //String price = df.format(orderParam.getPrice())+"";
            //金额
            Integer price;
            //判断时间
            boolean fag = belongCalendar(new Date(),courseBO.getDiscountStartTime(),courseBO.getDiscountEndTime());
            if(fag){
                //获取优惠语言价格
                price = courseBO.getDiscountPrice();
                System.err.println(price);
            }else{
                price = courseBO.getPrice();

            }
            model.setSubject("curriculum");
            model.setOutTradeNo(number);
            System.err.println(price+".00");
            model.setTotalAmount(price+".00");
            //model.setTotalAmount("0.01");
            model.setProductCode("QUICK_MSECURITY_PAY");
            String courseId = orderParam.getCourseId()+"";
            try {
                String passback_params =java.net.URLEncoder.encode(courseId,"UTF-8");
                //传入课程id
                model.setPassbackParams(passback_params);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            orderParam.setPrice(new BigDecimal(price));
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AlipayConfig.fy_url);

            // 调用SDK生成表单
            AlipayTradeAppPayResponse ali_response = client.sdkExecute(ali_request);
            if (ali_response.isSuccess()) {
                //插入充值预支付订单
                payService.prepaid(orderParam);
                // 获取到getBody直接给app,用这个东西去调起支付宝
                //System.out.println(ali_response.getBody());
                //return ali_response.getBody();
               // super.safeJsonPrint(response, ali_response.getBody());
                return ali_response.getBody();
            } else {
                log.debug("调用SDK生成表单失败");
                throw new AlipayApiException("调用SDK生成表单失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * 判断时间是否在某一区间内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
