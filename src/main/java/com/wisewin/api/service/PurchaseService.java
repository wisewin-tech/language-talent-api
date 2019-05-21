package com.wisewin.api.service;

import com.wisewin.api.dao.*;
import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.PruchaseDTO;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
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

    //获取当前用户
    //获取要购买课程的id
    //判断当前用户是否购买了当前课程
    //判断当前课程是否在优惠时间，
    //获取优惠价格或者普通价格
    //判断当前用户的咖豆是否足够购买当前课程

    /**
     * 获取当前用户信息
     */
    public UserBO selectUser(Integer id) {
        return userDAO.selectUser(id);
    }

    /**
     * '判断当前用户是否购买当前课程
     * @param userId
     * @param coursesId
     * @return
     */
   /*public boolean purchase(Integer userId, String coursesId){
      OrderCoursesBO orderCoursesBO = orderCoursesDAO.selectOrderCourses(userId, coursesId) ;
      if(orderCoursesBO != null){
          //用户已购买
          return true;
      }
      //用户未购买
      return false;
   }*/

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
        PruchaseDTO pruchase = new PruchaseDTO();
        //获取要购买的课程

        //获取特惠开始时间
        Date dateStart = DateUtil.getDate(course.getDiscountStartTime());
        //获取特惠结束时间
        Date dateEnd = DateUtil.getDate(course.getDiscountEndTime());
        //判断是否在特惠时间内
        boolean falg = belongCalendar(new Date(), dateStart, dateEnd);

        StringBuffer sbf = new StringBuffer();
        sbf.append(course.getForeignName());
        sbf.append(" | ");
        sbf.append(course.getCourseName());
        pruchase.setTitle(sbf.toString());
        //传入当前用户的咖豆
        pruchase.setUserCurrency(user.getCurrency());
        //是特惠时间
        if (falg) {
            //获取课程优惠价
            pruchase.setCoursePrice(course.getDiscountPrice());
            //判断用户咖豆是否能够买当前课程
            if (user.getCurrency() >= course.getDiscountPrice()) {
                pruchase.setState(true);
                return pruchase;
            } else {
                pruchase.setState(false);
                return pruchase;
            }
        }
        //获取课程正常价
        pruchase.setCoursePrice(course.getPrice());
        //判断用户咖豆是否大于等于课程正常价
        if (user.getCurrency() >= course.getPrice()) {
            pruchase.setState(true);
            return pruchase;
        } else {
            pruchase.setState(false);
            return pruchase;
        }
    }


    //获取当前用户
    //获取要购买课程的id
    //判断当前用户是否购买了当前课程
    //判断当前课程是否在优惠时间，
    //获取优惠价格或者普通价格
    //判断当前用户的咖豆是否足够购买当前课程

    /**
     * @param language 语言
     * @param user     当前用户
     * @return
     */
    public PruchaseDTO isLanguage(LanguageBO language, UserBO user) {

        //获取特惠开始时间
        Date dateStart = language.getDiscountStartTime();
        //获取特惠结束时间
        Date dateEnd = language.getDiscountEndTime();
        //判断是否在特惠时间内
        boolean falg = belongCalendar(new Date(), dateStart, dateEnd);
        PruchaseDTO pruchase = new PruchaseDTO();
        pruchase.setTitle(language.getLanguageName());
        //传入当前用户的咖豆
        pruchase.setUserCurrency(user.getCurrency());
        //是特惠时间
        if (falg) {
            //获取语言优惠价
            pruchase.setCoursePrice(language.getLanguageDiscountPrice());
            //判断用户咖豆是否能够买当前语言
            if (user.getCurrency() >= language.getLanguageDiscountPrice()) {
                pruchase.setState(true);
                return pruchase;
            } else {
                pruchase.setState(false);
                return pruchase;
            }
        }
        //获取语言正常价
        pruchase.setCoursePrice(language.getLanguagePrice());
        //判断用户咖豆是否大于等于语言正常价
        if (user.getCurrency() >= language.getLanguagePrice()) {
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
    public void insertOrderCouse(CourseBO course, String userId, PruchaseDTO pruchase) {

        OrderBO order = new OrderBO();
        order.setUserId(Integer.parseInt(userId));
        order.setPrice(new BigDecimal(pruchase.getCoursePrice()));
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        order.setOrderNumber(idBuilder.nextId() + "");
        order.setStatus("yes");
        order.setOrderType("购买");
        order.setProductName(pruchase.getTitle());

        order.setCreateTime(DateUtil.getDateStr(new Date()));
        order.setUpdateTime(DateUtil.getDateStr(new Date()));
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
        orderCourses.setCourseValidityPeriod(overDate(course.getCourseValidityPeriod()));
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
    }

    /**
     * 下订单（语言）
     *
     * @param
     * @return
     */
    public void insertOrderlanguage(String languageId, String userId, PruchaseDTO pruchase) {
        //获取购买的语言
        List<CourseBO> list = courseDAO.listCousebyLanguage(languageId);
        System.out.println(list);
        OrderBO order = new OrderBO();
        order.setUserId(Integer.parseInt(userId));
        order.setPrice(new BigDecimal(pruchase.getCoursePrice()));
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        order.setOrderNumber(idBuilder.nextId() + "");
        order.setStatus("yes");
        order.setOrderType("购买");
        order.setProductName(pruchase.getTitle());
        order.setCreateTime(DateUtil.getDateStr(new Date()));
        order.setUpdateTime(DateUtil.getDateStr(new Date()));
        //获取返回的主订单id
        orderDAO.insertOrder(order);

        if (list != null) {
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
                //有效日期
                Date date1 = overDate(course.getCourseValidityPeriod());
                orderCourses.setCourseValidityPeriod(date1);
                lists.add(orderCourses);
            }
            orderCoursesDAO.insetListOrderCourse(lists);
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
