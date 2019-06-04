package com.wisewin.api.dao;



import com.wisewin.api.entity.bo.VersionsBO;

import java.util.List;

/**
 * 版本
 */
public interface VersionsDAO {

    /**
     * 查询版本
     */
    List<VersionsBO> queryVersions();




}
