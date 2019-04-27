package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.CertificateBO;

public interface CertificateDAO {
    /**
     * 查询证书图片
     * @param certificateId
     * @return
     */
    String certificateImage(Integer certificateId);
}
