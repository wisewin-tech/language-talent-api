package com.wisewin.api.dao;



import com.wisewin.api.entity.bo.VersionsBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 版本
 */
public interface VersionsDAO {

    /**
     * 查询版本
     */
    VersionsBO queryVersions(@Param("versioncode") Integer versioncode,@Param("platform") String platform);




}
