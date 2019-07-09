package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.ShareBO;
import org.apache.ibatis.annotations.Param;


public interface ShareDAO {

    /**
     * 查询分享图
     */
    ShareBO queryShare(@Param("key") String key);



}
