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
    public List<SignResultBO> selectMon(Integer userId) {

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
            set.add(DateUtils.getDateStr(signBO.getSignTime()));
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

        return resultList;
    }


    //用户签到
    public boolean signIn(Integer userId) {
        //获取今天的起始时间
        Date start= TimeUtil.getTimeStart(0);
        //获取今天的结束时间
        Date end= TimeUtil.getTimeEnd(0);
        //查询签到表用户最新记录
        SignBO signBO=signDAO.selectNew(userId);
        Date signTime=signBO.getSignTime();
        //true在时间段内，false不在时间段内

        if (hourMinuteBetween(signTime,start,end)){
            //如果最新记录的签到时间为当天
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取昨天的起始时间
        Date startdate= TimeUtil.getTimeStart(-1);
        //获取昨天的结束时间
        Date enddate= TimeUtil.getTimeEnd(-1);

        UserSignBO userBO=signDAO.selectUser(userId);

        //连续签到天数
        Integer continuousSign=userBO.getContinuousSign();
        //累计签到天数
        Integer cumulativeSign=userBO.getCumulativeSign();
        //上次签到日期
        Date date=userBO.getLastSign();
        //用户积分
        Integer integral=userBO.getIntegral();
        //true在时间段内，false不在时间段内
        if (!hourMinuteBetween(date,startdate,enddate)){

            //连续签到天数改为1
            userBO.setContinuousSign(1);
        }else {
            //连续签到天数改为+1
            userBO.setContinuousSign(continuousSign + 1);
        }
            //累计签到天数+1
            userBO.setCumulativeSign(cumulativeSign+1);
            //积分+10
            userBO.setIntegral(integral+ UserConstants.SIGNNUM.getNum());
            //修改用户签到信息
            signDAO.updateUserSign(userBO);
            RecordBO recordBO=new RecordBO();
            recordBO.setUserId(userId);
            recordBO.setSource(UserConstants.INTEGRAL.getValue());
            recordBO.setStatus(UserConstants.INCREASE.getValue());
            recordBO.setDescribe("签到所获积分");
            recordBO.setSpecificAmount(UserConstants.SIGNNUM.getNum());
            System.out.println(recordBO);
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


}
