package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.CertificateResultBO;

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
    List<CertificateResultBO> selectUser(Integer userId);
}
