package com.wisewin.api.service;

import com.wisewin.api.dao.CouplebacDAO;
import com.wisewin.api.entity.bo.CouplebackBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("CouplebackService")
@Transactional
public class CouplebackService {

    @Resource
    private CouplebacDAO couplebacDAO;


    /**
     * 添加反馈信息
     * String userId  //用户id
     * String content; //反馈内容
     * String contactWay//什么方式
     * String contactpattern; //用户联系方式
     * String pictureUrl; //图片url
     */
    public  boolean getaddCpupleback(Integer userId, String content,String contactWay,String contactpattern, String pictureUrl){

        CouplebackBO couplebackBO=new CouplebackBO(userId, content,contactWay,contactpattern,pictureUrl);

        return  couplebacDAO.addCpupleback(couplebackBO)>0;

    }

    /**
     * 显示反馈意见
     *   Integer id; //意见反馈id
      String content; //反馈内容
      String contactpattern; //用户联系方式
      String pictureUrl; //图片url
      Date cbUpdatetime; //修改时间
      String disposeresUlt; //处理结果
      String disposeperson; //处理人
     */
    public List<CouplebackBO> getqueryCpupleback(Integer id ,String content,String contactpattern,String pictureUrl,Date cbUpdatetime,String disposeresUlt,String disposeperson){
        CouplebackBO couplebackBO=new CouplebackBO(id,content,contactpattern,pictureUrl,cbUpdatetime,disposeresUlt,disposeperson);
        return  couplebacDAO.queryCpupleback(couplebackBO);
    }

}
