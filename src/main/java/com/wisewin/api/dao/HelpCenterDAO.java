package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.HelpCenterBO;
import java.util.List;

public interface HelpCenterDAO {
    /**
     * 帮助中心标题展示
     * @return
     */
    List<HelpCenterBO> selectHelpCenter();



    /**
     * 帮助中心文本展示
     * @param id
     * @return
     */
    HelpCenterBO getparticulars(Integer id);
}
