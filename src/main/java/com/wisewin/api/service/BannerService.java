package com.wisewin.api.service;

import com.wisewin.api.dao.BannerDAO;
import com.wisewin.api.entity.bo.BannerBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("bannerService")
@Transactional
public class BannerService {
    @Resource
    private BannerDAO bannerDAO;

    /**
     * banner图展示
     * @return
     */
    public List<BannerBO> getBanner(){
        return bannerDAO.getBanner();
    }
}
