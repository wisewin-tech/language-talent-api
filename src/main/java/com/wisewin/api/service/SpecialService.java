package com.wisewin.api.service;

import com.wisewin.api.dao.SpecialDAO;
import com.wisewin.api.entity.bo.SpecialBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("SpecialService")
@Transactional
public class SpecialService {

    @Resource
    SpecialDAO specialDAO;



    /**
     * 专题的查询
     * */
    public List<SpecialBO> selectSpecialBO(){
        return specialDAO.selectSpecialBO();
    }




}
