
package com.wisewin.api.service;

import com.wisewin.api.dao.*;
import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.PruchaseDTO;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.date.DateUtil;
import com.wisewin.api.web.controller.WBAlipayController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 王彬 on 2019/5/9.
 */
@Transactional
@Service("purchaseService")
public class PurchaseService {

    static final Logger log = LoggerFactory.getLogger(PurchaseService.class);

    @Resource
    private UserDAO userDAO;

    @Resource
    private CourseDAO courseDAO;

    @Resource
    private LanguageDAO languageDAO;

    @Resource
    private OrderDAO orderDAO;

    @Resource
    private OrderCoursesDAO orderCoursesDAO;

    @Resource
    private RecordDAO recordDAO;

    @Resource
    private CertificateDAO certificateDAO;

    @Resource
    private KeyValDAO keyValDAO;

    @Resource
    private OrderService orderService;

    @Resource
    private CertificateService certificateService;



    /**
     * 获取当前用户信息
     */
    public UserBO selectUser(Integer id) {
        System.err.println("当前用户是"+userDAO.selectUser(id));
        return userDAO.selectUser(id);
    }

    /**
     * 查询课程
     */
    public CourseBO queryCouse(String id) {
        return courseDAO.selectCourse(id);
    }

    /**
     * 查询语言
     */
    public LanguageBO queryLanguare(String id) {
        return languageDAO.selectLanguageG(id);
    }


    /**
     * 课程购买
     *
     * @param course 课程
     * @param user   当前用户
     * @return
     */
    public PruchaseDTO isCourse(CourseBO course, UserBO user) {
        boolean falg = false ;
        log.info("start=========================================com.wisewin.api.service.PurchaseService.isCourse===================================");
        log.info("参数course:{}",course);
        log.info("参数user:{}",user);
        PruchaseDTO pruchase = new PruchaseDTO();
        //获取要购买的课程
        log.info("获取要购买的课程:{}",pruchase);
        //获取特惠开始时间
        Date dateStart = course.getDiscountStartTime();
        log.info("获取特惠开始时间:{}",dateStart);
        //获取特惠结束时间
        Date dateEnd = course.getDiscountEndTime();
        log.info("获取特惠结束时间:{}",dateEnd);
        //判断是否在特惠时间内
        if(dateStart != null){
            falg  = belongCalendar(new Date(), dateStart, dateEnd);
        }

        log.info("判断特惠结束时间:{}",falg);
        LanguageBO languageBO = languageDAO.selectLanguageG(course.getLanguageId());
        log.info("获取课程对应的语言:{}",languageBO);
        if(languageBO == null){
            log.info("languageBO == null,return");
            return null;
        }

        StringBuffer sbf = new StringBuffer();
        sbf.append(languageBO.getLanguageName());
        sbf.append(" | ");
        sbf.append(course.getCourseName());
        pruchase.setTitle(sbf.toString());
        //传入当前用户的咖豆
        pruchase.setUserCurrency(user.getCurrency());
        pruchase.setImg(course.getThumbnailImageUrl());
        //是特惠时间
        if (falg) {
            log.info("如果是特惠时间");
            //获取课程优惠价
            pruchase.setCoursePrice(course.getDiscountPrice());
            log.info("获取课程优惠价:{}",course.getDiscountPrice());
            //判断用户咖豆是否能够买当前课程
            log.info("判断用户咖豆是否能够买当前课程");
            if (user.getCurrency() >= course.getDiscountPrice()) {
                pruchase.setState(true);
                log.info("reutrn true:{}",pruchase);
                log.info("end==========================================com.wisewin.api.service.PurchaseService.isCourse===============================================");
                return pruchase;
            } else {
                pruchase.setState(false);
                log.info("reutrn false:{}",pruchase);
                log.info("end==========================================com.wisewin.api.service.PurchaseService.isCourse===============================================");
                return pruchase;
            }
        }

        //获取课程正常价
        pruchase.setCoursePrice(course.getPrice());
        log.info("获取课程正常价:{}",course.getPrice());
        //判断用户咖豆是否大于等于课程正常价
        log.info("判断用户咖豆是否大于等于课程正常价");
        if (user.getCurrency() >= course.getPrice()) {
            pruchase.setState(true);
            log.info("return true:{}",pruchase);
            log.info("end==========================================com.wisewin.api.service.PurchaseService.isCourse===============================================");
            return pruchase;
        } else {
            pruchase.setState(false);
            log.info("return false:{}",pruchase);
            log.info("end==========================================com.wisewin.api.service.PurchaseService.isCourse===============================================");
            return pruchase;
        }
    }


    /**
     * @param language 语言
     * @param user     当前用户
     * @return
     */
    public PruchaseDTO isLanguage(LanguageBO language, UserBO user) {

        boolean falg = false;

        //获取特惠开始时间
        Date dateStart = language.getDiscountStartTime();
        //获取特惠结束时间
        Date dateEnd = language.getDiscountEndTime();
        if(dateStart != null){
            //判断是否在特惠时间内
            falg  = belongCalendar(new Date(), dateStart, dateEnd);
        }

        //查询用户已经购买过的课程
        List<CourseBO> corList =  orderDAO.getBeforeBuyCourseInfo(user.getId(),language.getId());
        System.err.println(corList);
        //查询用户购买过的课程的价格
        List<Integer>  intList =  orderDAO.getBeforeBuyCoursePrice(user.getId(),language.getId());
        Integer price  = 0 ;
        if(intList.size() >0 && intList != null){
            for(Integer i : intList){
                price = price + i ;
            }
        }
        System.err.println("用户购买过的课程的价格是"+price);
        List<CourseBO> list = orderService.getBeforeBuyCourseInfo(user.getId(),language.getId());

        PruchaseDTO pruchase = new PruchaseDTO();
        pruchase.setTitle(language.getLanguageName());
        //传入当前用户的咖豆
        pruchase.setUserCurrency(user.getCurrency());
        pruchase.setImg(language.getThumbnailImageUrl());
        if(list  == null || list.size()<= 0 ){
            pruchase.setMsg("您已购买了当前语言下的所有课程");
            pruchase.setCoursePrice(0);
            pruchase.setState(true);
            return pruchase;
        }

        //是特惠时间
        if (falg) {
           Integer  pr  = language.getLanguageDiscountPrice() - price > 0 ? language.getLanguageDiscountPrice() - price:0;
            System.err.println("应支付的价格是"+pr);
            System.err.println("语言价格"+language.getLanguageDiscountPrice());
            if(corList.size() >0 && corList != null){
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("您已购买了当前语言下的");
                for (CourseBO s : corList){
                    stringBuffer.append(",");
                    stringBuffer.append(s.getCourseName());
                }
                stringBuffer.append(",价格扣减后还需支付"+pr+"咖豆");
                pruchase.setMsg(stringBuffer.toString());
                System.err.println(stringBuffer.toString());
               //获取语言优惠价
               pruchase.setCoursePrice(pr);
            }

            //判断用户咖豆是否能够买当前语言
            if (user.getCurrency() >= pr) {
                pruchase.setState(true);
                return pruchase;
            } else {
                pruchase.setState(false);
                return pruchase;
            }
        }
        System.err.println("扣减的价格"+price);
        Integer pr  = language.getLanguagePrice() - price>0?language.getLanguagePrice() - price:0;
        System.err.println("应支付的价格是"+pr);
        if(corList.size() >0 && corList != null){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("您已购买了当前语言下的");
            for (CourseBO s : corList){
                stringBuffer.append(",");
                stringBuffer.append(s.getCourseName());
            }
            stringBuffer.append(",价格扣减后还需支付"+pr+"咖豆");
            System.err.println(stringBuffer.toString());
            pruchase.setMsg(stringBuffer.toString());
        }
        //获取语言正常价
        pruchase.setCoursePrice(pr);
        //判断用户咖豆是否大于等于语言正常价
        if (user.getCurrency() >= pr) {
            pruchase.setState(true);
            return pruchase;
        } else {
            pruchase.setState(false);
            return pruchase;
        }
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


    /**
     * 扣减用户咖豆
     *
     * @param price  价格
     * @param userId 用户id
     */
    public void deleteCurrencyCourse(String userId, Integer price) {
        userDAO.updateUserCrrency(userId, price);
    }


    /**
     * 插入订单(课程)
     */
    public void insertOrderCouse(CourseBO course, String userId, PruchaseDTO pruchase, String model) throws ParseException {
        OrderBO order = new OrderBO();
        order.setUserId(Integer.parseInt(userId));
        order.setPrice(new BigDecimal(pruchase.getCoursePrice()));
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        order.setOrderNumber(idBuilder.nextId() + "");
        order.setStatus("yes");
        order.setOrderType("咖豆购买");
        order.setProductName(pruchase.getTitle());
        order.setCreationDate(DateUtil.getDateStr(new Date()));
        order.setCreateTime(DateUtil.getDateStr(new Date()));
        order.setPurchaseChannels(model);
        //插入课程语言id
        order.setLcId(course.getId());
        //插入购买的类型（语言/课程）
        order.setType("curriculum");
        //获取返回的主订单id
        orderDAO.insertOrder(order);
        System.out.println(order.getId());
        OrderCoursesBO orderCourses = new OrderCoursesBO();
        orderCourses.setUserId(Integer.parseInt(userId));
        orderCourses.setOrderId(order.getId());
        orderCourses.setCoursesId(course.getId());
        orderCourses.setCoursesName(course.getCourseName());
        orderCourses.setCreateTime(new Date());
        orderCourses.setUpdateTime(new Date());
        // TODO
        String time = keyValDAO.selectKey("period_of_validity");
        //有效日期
        orderCourses.setCourseValidityPeriod(overDate(Integer.parseInt(time)));
        log.info("orderCourse:{}",orderCourses);
        orderCoursesDAO.insetOrderCourse(orderCourses);

        //扣减咖豆
        deleteCurrencyCourse(userId, pruchase.getCoursePrice());

        //添加用户消费记录
        RecordBO record = new RecordBO();
        record.setUserId(Integer.parseInt(userId));
        record.setSource("咖豆");
        record.setStatus("支出");
        record.setSpecificAmount(-pruchase.getCoursePrice());
        record.setDescribe("用户购买课程");
        recordDAO.insertUserAction(record);

        //添加证书
        CertificateBO certificateBO=new CertificateBO();
        certificateBO.setUserId(order.getUserId());
        certificateBO.setCourseId(course.getId());
        List<CertificateBO> certificateBOList=new ArrayList<CertificateBO>();
        certificateBOList.add(certificateBO);

        //证书编号
        String certificateNumber=certificateService.getCertificateNumber();
        certificateBO.setCertificateNumber(certificateNumber);
        certificateDAO.addCertificate(certificateBOList);
    }

    /**
     * 插入订单（语言）
     *
     * @param
     * @return
     */
    public void insertOrderlanguage(String languageId, String userId, PruchaseDTO pruchase, String model) throws ParseException {
     /* //获取用户已经购买过并且未过有效期的课程
        List<CourseBO> corList = orderDAO.getBeforeBuyCourseInfo(Integer.parseInt(languageId),Integer.parseInt(userId));

        //获取购买的语言下所有的课程
        List<CourseBO> list = courseDAO.listCousebyLanguage(languageId);*/

        List<CourseBO> list = orderService.getBeforeBuyCourseInfo(Integer.parseInt(userId),Integer.parseInt(languageId));
        if(list  == null || list.size()<= 0 ){
            return ;
        }
        System.err.println("渠道"+model);
        //System.out.println(list);
        OrderBO order = new OrderBO();
        order.setUserId(Integer.parseInt(userId));
        order.setPrice(new BigDecimal(pruchase.getCoursePrice()));
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        order.setOrderNumber(idBuilder.nextId() + "");
        order.setStatus("yes");
        order.setOrderType("咖豆购买");
        order.setProductName(pruchase.getTitle());
        order.setPurchaseChannels(model);

        //插入课程语言id
        order.setLcId(Integer.parseInt(languageId));
        //插入购买的类型（语言/课程）
        order.setType("language");
        //获取返回的主订单id
        orderDAO.insertOrder(order);

        if ((!(list.size() <= 0)) && list != null) {
            //证书
            List<CertificateBO> certificateBOList = new ArrayList<CertificateBO>();
            List<OrderCoursesBO> lists = new ArrayList<OrderCoursesBO>();
            for (int i = 0; i < list.size(); i++) {
                CourseBO course = list.get(i);
                OrderCoursesBO orderCourses = new OrderCoursesBO();
                orderCourses.setCoursesId(course.getId());
                orderCourses.setCoursesName(course.getCourseName());
                orderCourses.setOrderId(order.getId());
                orderCourses.setUserId(Integer.parseInt(userId));
                orderCourses.setCreateTime(new Date());
                orderCourses.setUpdateTime(new Date());
                //TODO
                String time = keyValDAO.selectKey("period_of_validity");
                //有效日期
                Date date1 = overDate(Integer.parseInt(time));
                orderCourses.setCourseValidityPeriod(date1);
                lists.add(orderCourses);
                //证书编号
                String certificateNumber = certificateService.getCertificateNumber();
                System.err.println("证书标号"+certificateNumber);
                //实例化证书
                CertificateBO certificateBO=new CertificateBO();
                certificateBO.setUserId(order.getUserId());
                certificateBO.setCourseId(course.getId());
                certificateBO.setCertificateNumber(certificateNumber);
                certificateBOList.add(certificateBO);
            }
            orderCoursesDAO.insetListOrderCourse(lists);
            //添加证书
            certificateDAO.addCertificate(certificateBOList);
        }

        //扣减咖豆
        deleteCurrencyCourse(userId, pruchase.getCoursePrice());

        //添加用户消费记录
        RecordBO record = new RecordBO();
        record.setUserId(Integer.parseInt(userId));
        record.setSource("咖豆");
        record.setStatus("支出");
        record.setSpecificAmount(-pruchase.getCoursePrice());
        record.setDescribe("用户购买语言");
        recordDAO.insertUserAction(record);

    }

    //获取到期时间
    public Date overDate(Integer in) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + in);
        Date today = calendar.getTime();
        return today;
    }
}

