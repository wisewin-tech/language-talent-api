package com.wisewin.api.service;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.dao.RecordDAO;
import com.wisewin.api.dao.SignDAO;
import com.wisewin.api.entity.bo.RecordBO;
import com.wisewin.api.entity.bo.SignBO;
import com.wisewin.api.entity.bo.SignResultBO;
import com.wisewin.api.entity.bo.UserSignBO;
import com.wisewin.api.util.DateUtils;
import com.wisewin.api.util.TimeUtil;
import com.wisewin.api.util.date.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SignService {
    @Resource
    private SignDAO signDAO;
    @Resource
    private RecordDAO recordDAO;

    /**
     *
     * @param nowDate   要比较的时间
     * @param startDate   开始时间
     * @param endDate   结束时间
     * @return   true在时间段内，false不在时间段内
     * @throws Exception
     */
    public  boolean hourMinuteBetween(Date nowDate, Date startDate, Date endDate)  {
        if (nowDate==null||startDate==null||endDate==null){
            return false;
        }
        /*
            compareTo方法
            如果指定的数与参数相等返回0。
            如果指定的数小于参数返回 -1。
            如果指定的数大于参数返回 1。
         */

        if (nowDate.compareTo(startDate)==1&&nowDate.compareTo(endDate)==-1){
            return true;
        }
        return false;
    }

    //查询用户本月签到信息,返回签到对象集合
    public  Map<String,Object> selectMon(Integer userId) {

        //获取当前月份1号的时间
        SimpleDateFormat dfstart = new SimpleDateFormat("yyyy-MM-01 00:00:00 ");
        String monstart=dfstart.format(new Date());

        //获取当前月份的下个月1号的时间
        SimpleDateFormat dfend = new SimpleDateFormat("yyyy-MM-01 00:00:00 ");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        String monend=dfend.format(calendar.getTime());
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("userId",userId);
        map.put("monstart",monstart);
        map.put("monend",monend);
        //把这个月的起始时间和结束时间以及userId放入map中
         List<SignBO> signBOList= signDAO.selectMon(map);
        Set<String>  set=new HashSet<String>();
        for (SignBO signBO:signBOList){
            //signBO.getSignTime()
            set.add(DateUtil.getStrDate(signBO.getSignTime()));
        }
        //这个月的天数集合
        List<String> listOfMonth = TimeUtil.getDayListOfMonth();
        //结果集合,用于接收这个月的天数和是否签到
        List<SignResultBO>  resultList=new ArrayList<SignResultBO>();
        for(String  str:listOfMonth){
            SignResultBO  signResultBO=new SignResultBO();
            signResultBO.setDate(str);
            if(set.contains(str)){  //如果此字符串包含，此方法返回true，否则返回false。
                signResultBO.setFlag("yes");
            }else{
                signResultBO.setFlag("no");
            }
            resultList.add(signResultBO);
        }
        //查询用户表签到信息,返回userSign 用户签到对象
        UserSignBO userBO=signDAO.selectUser(userId);
        //最后签到时间格式转化成yyyy-MM-dd
        userBO.setLastSign(DateUtil.getStr(userBO.getLastSign()));
        //创建一个map集合,用于存在签到表和用户表的信息
        Map<String,Object> mapSign=new HashMap<String, Object>();
        //把用户表的签到信息和签到表的信息放在map中
        mapSign.put("resultList",resultList);
        mapSign.put("userBO",userBO);
        //从库里取出"签到获取积分"对应的值
        String signIntegral=signDAO.selectSignIntegral(UserConstants.SIGNNUM.getValue());
        mapSign.put("SignIntegral",signIntegral);
        return mapSign;
    }


    /**
     * 用户签到
     * @param userId
     * @return
     */
    public boolean signIn(Integer userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取今天的日期
        String now=dateFormat.format(new Date());
        //查询签到表用户最新记录
        SignBO signBO=signDAO.selectNew(userId);
        //通过用户id查询用户相关签到信息
        UserSignBO userBO = signDAO.selectUser(userId);
        //如果用户不是首次签到
        if (signBO!=null){
            //用户表上次签到日期
            String userSignBOTime = userBO.getLastSign();
            //签到表签到日期
            String signTime = signBO.getSignTime();
            //如果最新记录的签到时间为当天或者上次签到时间是今天 false:今天已经签到
            if (now.equals(signTime) || now.equals(userSignBOTime)) {
                return false;
            }
        }
        //本周连续签到天数
        Integer weekContinuousSign = userBO.getWeekContinuousSign();
        //连续签到天数
        Integer continuousSign=userBO.getContinuousSign();
        //累计签到天数
        Integer cumulativeSign=userBO.getCumulativeSign();
        //上次签到日期
        String date=DateUtil.getStr(userBO.getLastSign());

        //用户积分
        Integer integral=userBO.getIntegral();
        //获取昨天日期
        Date yesterday= TimeUtil.getTimeStart(-1);
        String yesterdays= dateFormat.format(yesterday);
        //true昨天签到，false昨天没签到
        if (yesterdays.equals(date)){
            //连续签到天数改为+1
            userBO.setContinuousSign(continuousSign + 1);
            //获取本周周一的日期
            String monday = DateUtil.getWeekStart(new Date());
            System.out.println("1:"+DateUtil.getDate(monday).getTime());
            System.out.println("2:"+yesterday.getTime());
            if (DateUtil.getDate(monday).getTime()<yesterday.getTime()){
                userBO.setWeekContinuousSign(weekContinuousSign+1);
            }

        }else {
            //连续签到天数改为1
            userBO.setContinuousSign(1);
            userBO.setWeekContinuousSign(1);

        }
            //累计签到天数+1
            userBO.setCumulativeSign(cumulativeSign+1);
            //从库里取出"sign_integral"对应的值
            String signIntegral=signDAO.selectSignIntegral(UserConstants.SIGNNUM.getValue());
            Integer signNum=Integer.parseInt(signIntegral);
            //积分+10
            userBO.setIntegral(integral+ signNum);
            //修改用户签到信息
            signDAO.updateUserSign(userBO);
            RecordBO recordBO=new RecordBO();
            recordBO.setUserId(userId);
            recordBO.setSource(UserConstants.INTEGRAL.getValue());
            recordBO.setStatus(UserConstants.INCREASE.getValue());
            recordBO.setDescribe("签到获取积分");
            recordBO.setSpecificAmount(signNum);
            System.out.println("recordBO:"+ recordBO);
            recordDAO.insertUserAction(recordBO);
            //签到表添加用户签到
            signDAO.insertSign(userId);
            return true;

    }

    /**
     * 返回连续签到天数
     * @param userId
     * @return
     */
    public Integer getContinuousSign(Integer userId){
        if (userId==null){
            return null;
        }
        //用户表签到信息
        UserSignBO userSignBO=signDAO.selectUser(userId);
        //返回连续签到天数
        return userSignBO.getContinuousSign();
    }

    //查询用户本周签到信息
    public Integer selectWeek(Integer userId){
        Integer continuousSigndays = 0;
        Date date = new Date();
        String weekstart = DateUtil.getWeekStart(date);
        String weekend = DateUtil.getWeekEnd(date);
        Map map = new HashMap();
        map.put("weekstart",weekstart);
        map.put("weekend",weekend);
        map.put("userId",userId);

      // TODO: 2019/6/11
        List<SignBO> list = signDAO.selectWeek(map);
        for(SignBO signBO:list){
            if (DateUtil.getDateStr(new Date()).equals(signBO.getSignTime())){
                continuousSigndays= continuousSigndays+1;
            }
        }
        return continuousSigndays;
    }

    /**
     * 查询签到表用户最新记录
     * @param userId
     * @return
     */
    public SignBO selectNew(Integer userId){
        return  signDAO.selectNew(userId);
    }



}
