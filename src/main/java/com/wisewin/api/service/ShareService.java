package com.wisewin.api.service;

import com.wisewin.api.dao.ShareDAO;
import com.wisewin.api.entity.bo.ShareBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("ShareService")
@Transactional
public class ShareService {
    @Resource
    private ShareDAO shareDAO;

    /**
     * 查询分享图
     */
    public  ShareBO queryShare(String key){
        return shareDAO.queryShare(key);
    }


}