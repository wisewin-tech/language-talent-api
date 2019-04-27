package com.wisewin.api.web.controller;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.bo.common.constants.SysConstants;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.UserParam;
import com.wisewin.api.service.UserService;
import com.wisewin.api.util.AccountValidatorUtil;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.ParamNullUtil;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.util.redisUtils.RedissonHandler;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Shibo Sun
 *         主机controller
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseCotroller {

    @Resource
    private UserService userService;
    /**
     * 手机号格式判断
     */
    private boolean phoneFormt(String phone,HttpServletResponse response)  {
        if (!(AccountValidatorUtil.isMobile(phone))){
                    //如果手机号格式不正确,提示.返回false
                    String json = JsonUtils.getJsonString4JavaPOJO
                            (ResultDTOBuilder.failure("0000001", "手机号格式不正确"));
               super.safeJsonPrint(response, json);

            return false;
        }
        //手机号格式通过,返回true
        return true;
    }

    /**
     * 将user对象存入到Cookie
     * @param response
     * @param userBO
     */
    private void putUser(HttpServletResponse response,UserBO userBO){
        String uuid = UUID.randomUUID().toString();
        System.out.println("UUID:"+uuid);
        super.putLoginUser(uuid, userBO);
        super.setCookie(response, SysConstants.CURRENT_LOGIN_CLIENT_ID, uuid,24 * 60 * 60);
    }


    /**
     * 发送验证码
     * @param phone
     *
     */
    @RequestMapping("/send")
    public void send(String phone,HttpServletResponse response)  {
        //手机号非空+格式判断
        if (this.phoneFormt(phone,response)){
            userService.send(phone);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("验证码发送成功!"));
            super.safeJsonPrint(response, json);
        }


}

    /**
     * 手机号验证通过,对比用户验证码,登录或添加用户信息
     * @param phone
     * @param verify 用户验证码
     */
    //用户注册或者登录
    @RequestMapping("/register")
    public void register(String phone,String verify,HttpServletResponse response,HttpServletRequest request){
        //手机号非空+格式判断
        this.phoneFormt(phone,response);
        //用户传参非空判断
        if (StringUtils.isEmpty(verify)) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        //获取Redis中的用户验证码
        String mobileAuthCode = RedissonHandler.getInstance().get(phone + UserConstants.VERIFY.getValue());
        System.out.println("register方法中,缓存中的验证码为"+mobileAuthCode);
        //如果和用户收到的验证码相同
        if(verify.equals(mobileAuthCode)){
           //通过手机号查询表中是否有该用户
             UserBO userBO = userService.selectPhone(phone);
            if(userBO!=null){
                //user对象存入cookie中
                this.putUser(response,userBO);
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("登录成功!"));
                super.safeJsonPrint(response, json);

            }else{ //如果表里没有该用户,添加用户手机号,把带有手机号的user对象存入cookie中,登录成功,
                UserBO userBO1=new UserBO();
                userBO1.setMobile(phone);
                userService.insertUser(userBO1);
                userBO1 = userService.selectPhone(phone);
                //将只带有手机号的user对象存入cookie中
                this.putUser(response,userBO1);
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("用户注册完成,登录成功"));
                super.safeJsonPrint(response, json);
            }
        }else{
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "验证码错误"));
            super.safeJsonPrint(response, json);
        }
    }

    /**
     *修改用户信息
     * @param response
     * @param request
     */
    @RequestMapping("/update")
    public void updateUser(HttpServletResponse response, HttpServletRequest request, UserParam userParam) {
        //从cookie中获取他的user对象的id
        Integer id=this.getId(request);
        //如果获取不到,参数异常
        if (id==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        //如果获取到了,判断user参数不为空
        if (ParamNullUtil.checkObjAllFieldsIsNull(userParam)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        //把id设置到user参数对象中
        userParam.setId(id);
        //修改信息
        UserBO userBO=new UserBO();

        userService.updateUser(userParam);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改信息成功"));
        super.safeJsonPrint(response, json);
    }


}
