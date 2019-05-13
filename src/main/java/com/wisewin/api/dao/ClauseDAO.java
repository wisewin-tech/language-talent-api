package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.ClauseBO;

import java.util.List;

//条款管理
public interface ClauseDAO {

    /**
     * 查询一条条款
     * */
    ClauseBO selectClauseBOByClassify(String classify);



}
