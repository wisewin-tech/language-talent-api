package com.wisewin.api.service;


import com.wisewin.api.dao.GiftDAO;
import com.wisewin.api.entity.bo.GiftBO;
import com.wisewin.api.entity.bo.GiftRecordBO;
import com.wisewin.api.entity.bo.UserBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class GiftService {

    @Resource
    private GiftDAO giftDAO;


    /**
     * 根据礼品卡码查找是否存在
     * exchangeyard 兑换码
     */
    public GiftBO getqueryGift(String exchangeyard){
        return  giftDAO.queryGift(exchangeyard);
    }

    /**
     * 在礼品卡记录表添加记录
     * Integer giftid; //礼品卡id
     * Integer userid; //当前用户id
     * Integer value; //兑换值
     */
    public boolean getaddGiftrecord(Integer giftid,Integer userid,Integer exchangevalue){
        GiftRecordBO giftRecordBO=new GiftRecordBO(giftid,userid,exchangevalue);
        return  giftDAO.addGiftrecord(giftRecordBO)>0;
    }

    /**
     * 修改礼品卡状态
     * String status  状态
     */
    public boolean getupdateGift(Integer id,String status){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        map.put("status",status);
        return  giftDAO.updateGift(map)>0;
    }

    /**
     * 修改咖豆数量
     * Integer id 用户id
     * Integer currency 咖豆
     */
    public  Integer getupdateUserGift(UserBO userBO, Integer currency){
        Integer curr= userBO.getCurrency();
        if(curr!=null){
            curr=curr+currency;
        }else{
            curr=currency;
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("id",userBO.getId());
        map.put("currency",curr);
        return  giftDAO.updateUserGift(map);
    }
}
