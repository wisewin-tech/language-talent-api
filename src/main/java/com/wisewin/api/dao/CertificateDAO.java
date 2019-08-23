package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CertificateDAO {
    /**
     * 查询证书图片
     * @param certificateId
     * @return
     */
    String certificateImage(Integer certificateId);
    /**
     * 查询用户证书信息
     */
    UserBO selectUser(Integer userId);

    /**
     * 添加证书
     */
    Integer addCertificate(List<CertificateBO> list);

    //查询用户证书
    List<CateBO> queryCateList(@Param("userId")String userId);

    /**
     * 查询用户证书
     */
    List<QCertificateBO>  queryCertificate(Integer userId);
    /**
     * 课程id查询语言id
     */
    Integer queryLanguageId(Integer id);

    /**
     * 查询课程信息
     */
    List<CourseCerBO>  queryCourseCer(Integer id);

    /**
     * 查询级别信息
     */
    List<LevelCerBO> queryLevelCer(Integer id);

    /**
     * 查询分数
     */
    BigDecimal queryScore(@Param("userId") Integer userId,@Param("id") Integer id);

    /**
     *  查询课程数量
     */
    Integer queryChaperCount(Integer id);

    String queryCourseName(Integer id);
}
