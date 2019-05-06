package com.wisewin.api.service;

import com.wisewin.api.dao.HelpCenterDAO;
import com.wisewin.api.entity.bo.HelpCenterBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("helpCenterService")
@Transactional
public class HelpCenterService {
    @Resource
    private HelpCenterDAO helpCenterDAO;

    /**
     * 查询帮助中心
     *
     * @return
     */
    public List<HelpCenterBO> selectHelpCenter() {
        return helpCenterDAO.selectHelpCenter();
    }
    /**
     * 查看文本详情
     * @param id
     * @return
     */
    public HelpCenterBO getparticulars(Integer id){
        return helpCenterDAO.getparticulars(id);
    }

}
