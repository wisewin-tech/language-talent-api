package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.AboutUsBO;

public interface AboutUsDAO {

    /**
     * 查询关于我们
     * @return
     */
    AboutUsBO selectAbout();
}
