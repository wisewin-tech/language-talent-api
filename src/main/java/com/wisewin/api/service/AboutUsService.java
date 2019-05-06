package com.wisewin.api.service;

import com.wisewin.api.dao.AboutUsDAO;
import com.wisewin.api.entity.bo.AboutUsBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("aboutUsService")
@Transactional
public class AboutUsService {
    @Resource
    private AboutUsDAO aboutUsDAO;

    /**
     * 查询关于我们
     * @return
     */
    public AboutUsBO selectContent() {
        return aboutUsDAO.selectAbout();
    }
}
