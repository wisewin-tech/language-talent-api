package com.wisewin.api.service;

import com.wisewin.api.dao.CertificateDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("certificateService")
@Transactional
public class CertificateService {

    @Resource
    private CertificateDAO certificateDAO;

    public String certificateImage(Integer certificateId){

        return certificateDAO.certificateImage(certificateId);
    }
}
