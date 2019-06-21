package com.wisewin.api.service;

import com.wisewin.api.dao.ClauseDAO;
import com.wisewin.api.entity.bo.ClauseBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//条款管理
@Service
public class ClauseService {
    @Resource
    ClauseDAO clauseDAO;

    /**
     * 查询一条条款
     * */
    public ClauseBO selectClauseBOByClassify(String classify){
        return clauseDAO.selectClauseBOByClassify(classify);
    }

}
