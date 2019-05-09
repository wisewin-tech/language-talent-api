package com.wisewin.api.service;

import com.wisewin.api.dao.SpecialClassDAO;
import com.wisewin.api.entity.bo.SpecialClassBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("SpecialClassService")
@Transactional
public class SpecialClassService {

    @Resource
    SpecialClassDAO specialClassDAO;


    /**
     * 专题分类的查询
     * */
    public List<SpecialClassBO> selectSpecialClassBO(){
        return specialClassDAO.selectSpecialClassBO();
    }


}
