package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.BannerBO;

import java.util.List;

public interface BannerDAO {
    /**
     * banner图
     * @return
     */
    List<BannerBO> getBanner();
}
