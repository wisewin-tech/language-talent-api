package com.wisewin.api.service;

import com.wisewin.api.dao.CertificateDAO;
import com.wisewin.api.entity.bo.CertificateResultBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("certificateService")
@Transactional
public class CertificateService {

    @Resource
    private CertificateDAO certificateDAO;

    public String certificateImage(Integer certificateId){

        return certificateDAO.certificateImage(certificateId);
    }

    /**
     * 查询用户证书
     * @param userId
     * @return
     */
    public List<CertificateResultBO> selectUser(Integer userId){

        return certificateDAO.selectUser(userId);
    }




}
