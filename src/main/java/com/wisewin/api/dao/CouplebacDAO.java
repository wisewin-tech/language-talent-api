package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.CouplebackBO;

import java.util.List;

/**
 * 反馈信息
 */
public interface CouplebacDAO {

    /**
     * 添加反馈信息
     * String content; //反馈内容
     * String contactWay//什么方式
     * String contactpattern; //用户联系方式
     * String pictureUrl; //图片url
     */
    Integer  addCpupleback(CouplebackBO couplebackBO);

    /**
     * 显示反馈意见
     *  private Integer id; //意见反馈id
        private Integer userId; //用户id
        private String content; //反馈内容
         private String contactpattern; //用户联系方式
         private String pictureUrl; //图片url
         private Date cbUpdatetime; //修改时间
         private String disposeresUlt; //处理结果
         private String disposeperson; //处理人
     */

    List<CouplebackBO> queryCpupleback(CouplebackBO couplebackBO);
}
