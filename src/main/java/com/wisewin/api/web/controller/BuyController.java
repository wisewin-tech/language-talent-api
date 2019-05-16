package com.wisewin.api.web.controller;

import com.wisewin.api.common.constants.RecordConstants;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.RecordParam;
import com.wisewin.api.service.RecordService;
import com.wisewin.api.service.UserService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.date.DateUtil;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 购买咖豆
 */
@Controller
@RequestMapping("/Buy")
public class BuyController  extends BaseCotroller
{

    @Resource
    private RecordService recordService;

    @Resource
    private UserService userService;




}
