package com.wisewin.api.service;
import com.wisewin.api.dao.CourseDAO;
import com.wisewin.api.dao.LanguageDAO;
import com.wisewin.api.dao.UserDAO;
import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.LanguageBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.PruchaseDTO;
import com.wisewin.api.util.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;


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

    //获取当前用户
    //获取要购买课程的id
    //判断当前用户是否购买了当前课程
    //判断当前课程是否在优惠时间，
    //获取优惠价格或者普通价格
    //判断当前用户的咖豆是否足够购买当前课程

    /**
     * 获取当前用户信息
     */
    public UserBO  selectUser(Integer id){
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
     *  课程购买
     * @param id  课程id
     * @param user 当前用户
     * @return
     */
    public PruchaseDTO isCourse(String id,UserBO user){
        //获取要购买的课程
        CourseBO course  = courseDAO.selectCourse(id);
        //获取特惠开始时间
        Date dateStart  = DateUtil.getDate(course.getDiscountStartTime());
        //获取特惠结束时间
        Date dateEnd   = DateUtil.getDate(course.getDiscountEndTime());
        //判断是否在特惠时间内
        boolean falg = belongCalendar(new Date(),dateStart,dateEnd);
        PruchaseDTO pruchase = new PruchaseDTO();
        StringBuffer  sbf = new StringBuffer();
        sbf.append(course.getForeignName());
        sbf.append(" | ");
        sbf.append(course.getCourseName());
        pruchase.setTitle(sbf.toString());
        //传入当前用户的咖豆
        pruchase.setUserCurrency(user.getCurrency());
        //是特惠时间
        if(falg){
            //获取课程优惠价
            pruchase.setCoursePrice(course.getDiscountPrice());
            //判断用户咖豆是否能够买当前课程
            if(user.getCurrency()>=course.getDiscountPrice()){
                pruchase.setState(true);
                return pruchase;
            }else{
                pruchase.setState(false);
                return pruchase;
            }
        }
        //获取课程正常价
        pruchase.setCoursePrice(course.getPrice());
        //判断用户咖豆是否大于等于课程正常价
        if(user.getCurrency()>=course.getPrice()){
            pruchase.setState(true);
            return pruchase;
        }else{
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
     *
     * @param id    语言id
     * @param user  当前用户
     * @return
     */
    public PruchaseDTO isLanguage(String id,UserBO user){
        //获取购买的语言语言
        LanguageBO language  =  languageDAO.selectLanguageG(id);
        //获取特惠开始时间
        Date dateStart  = language.getDiscountStartTime();
        //获取特惠结束时间
        Date dateEnd   = language.getDiscountEndTime();
        //判断是否在特惠时间内
        boolean falg = belongCalendar(new Date(),dateStart,dateEnd);
        PruchaseDTO pruchase = new PruchaseDTO();
        pruchase.setTitle( language.getLanguageName());
        //传入当前用户的咖豆
        pruchase.setUserCurrency(user.getCurrency());
        //是特惠时间
        if(falg){
            //获取语言优惠价
            pruchase.setCoursePrice(language.getLanguageDiscountPrice());
            //判断用户咖豆是否能够买当前语言
            if(user.getCurrency()>=language.getLanguageDiscountPrice()){
                pruchase.setState(true);
                return pruchase;
            }else{
                pruchase.setState(false);
                return pruchase;
            }
        }
        //获取语言正常价
        pruchase.setCoursePrice(language.getLanguagePrice());
        //判断用户咖豆是否大于等于语言正常价
        if(user.getCurrency()>=language.getLanguagePrice()){
            pruchase.setState(true);
            return pruchase;
        }else{
            pruchase.setState(false);
            return pruchase;
        }

    }

    /**
     * 判断时间是否在某一区间内
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
     * @param price  价格
     * @param userId 用户id
     */
    public void  deleteCurrencyCourse(String userId, Integer price){
        userDAO.updateUserCrrency(userId,price);
    }



}
