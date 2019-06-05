package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.CateBO;
import com.wisewin.api.entity.bo.CertificateBO;
import com.wisewin.api.entity.bo.CertificateResultBO;
import com.wisewin.api.entity.bo.UserBO;
import org.apache.ibatis.annotations.Param;

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

}
