package com.wisewin.api.web.controller;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.bo.common.constants.SysConstants;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.UserParam;
import com.wisewin.api.service.UpPictureService;
import com.wisewin.api.service.UserService;
import com.wisewin.api.util.*;
import com.wisewin.api.util.date.DateUtil;
import com.wisewin.api.util.redisUtils.RedissonHandler;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
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
    @Resource
    private UpPictureService upPictureService;
    /**
     * 手机号格式判断
     */
    private boolean phoneFormt(String phone,HttpServletResponse response)  {
        if (!(AccountValidatorUtil.isMobile(phone))){
                    //如果手机号格式不正确,提示.返回false
                    String json = JsonUtils.getJsonString4JavaPOJO
                            (ResultDTOBuilder.failure("0000010"));
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
        super.setCookie(response, SysConstants.CURRENT_LOGIN_CLIENT_ID, uuid,24 * 60 * 60*30);

        Object oldKey =RedissonHandler.getInstance().get(userBO.getId() + SysConstants.LOGIN_IDENTIFICATION);
        if(oldKey!=null){
            RedissonHandler.getInstance().delete((String)oldKey);
        }
        RedissonHandler.getInstance().set(userBO.getId()+ SysConstants.LOGIN_IDENTIFICATION,
                super.createKey(uuid, com.wisewin.api.common.constants.SysConstants.CURRENT_LOGIN_USER) , (long)24*60*60*30);


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
            //获取缓存中的失效验证码
            String mobileAuthCode = RedissonHandler.getInstance().get(phone + UserConstants.VERIFY_LOSE.getValue());
            if (mobileAuthCode!=null){
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000012"));
                super.safeJsonPrint(response, json);
                return;
            }
            userService.send(phone);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
            super.safeJsonPrint(response, json);

        }
}

    /**
     * 测试专用保存cookie接口
     * @param response
     * @param request
     */
    @RequestMapping("/login")
    public void loginCookie(HttpServletResponse response,HttpServletRequest request) {
        String phone="18631323025";
        //通过手机号查询表中是否有该用户
        UserBO userBO = userService.selectByPhone(phone);
        Map<String,Object> mapUser=new HashMap<String, Object>();
        if(userBO!=null){
                //islogin 是否为登录, yes 登录
                mapUser.put("islogin", UserConstants.Yes.getValue());
                //user对象存入cookie中
                this.putUser(response, userBO);
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
                super.safeJsonPrint(response, json);
        }else{
            //如果表里没有该用户,添加用户手机号,把带有手机号的user对象存入cookie中,登录成功,
            UserBO user=userService.selectById(26);
            //islogin 是否为登录, yes 登录
            mapUser.put("islogin",UserConstants.No.getValue());
            //将只带有手机号的user对象存入cookie中
            this.putUser(response,user);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
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
        System.out.println(phone + UserConstants.VERIFY.getValue());
        String mobileAuthCode = RedissonHandler.getInstance().get(phone + UserConstants.VERIFY.getValue());
        Map<String,Object> mapUser=new HashMap<String, Object>();

        //如果和用户收到的验证码相同
        if(verify.equals(mobileAuthCode)){
           //通过手机号查询表中是否有该用户
             UserBO userBO = userService.selectByPhone(phone);
            if(userBO!=null){
                //islogin 是否为登录, yes 登录
                if (userBO.getStatus().equals(UserConstants.Yes.getValue())){
                    //状态,是否被拉黑  yes:拉黑,no:账号正常使用
                    String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000014"));
                    super.safeJsonPrint(response, json);
                }else {
                    mapUser.put("islogin", UserConstants.Yes.getValue());
                    //user对象存入cookie中
                    this.putUser(response, userBO);
                    String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
                    super.safeJsonPrint(response, json);
                }
            }else{ //如果表里没有该用户,添加用户手机号,把带有手机号的user对象存入cookie中,登录成功,
                UserBO userBO1=new UserBO();
                userBO1.setMobile(phone);
                userService.insertUser(userBO1);
                userBO1 = userService.selectByPhone(phone);
                //islogin 是否为登录, yes 登录
                mapUser.put("islogin",UserConstants.No.getValue());
                //将只带有手机号的user对象存入cookie中
                this.putUser(response,userBO1);
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
                super.safeJsonPrint(response, json);
            }
        }else{
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000011"));
            super.safeJsonPrint(response, json);
        }
    }

    /**
     *修改用户信息
     * @param response
     * @param request
     */
    @RequestMapping("/update")
    public void updateUser(HttpServletResponse response,HttpServletRequest request, UserParam userParam) {
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
        userService.updateUser(userParam);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
        super.safeJsonPrint(response, json);
    }
    /**
     *上传头像
     * @param response
     * @param request
     */
    @RequestMapping("/upPicture")
    public void upPicture(HttpServletResponse response,HttpServletRequest request,MultipartFile image, UserParam userParam)
            throws Exception {
        //从cookie中获取他的user对象的id
        Integer id=this.getId(request);
        //如果获取不到,参数异常
        if (id==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        //图片非空判断
        if (image==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response,json);
        }
        OSSClientUtil ossClientUtil=new OSSClientUtil();
        //上传
        String name=ossClientUtil.uploadImg2Oss(image);
        //name:图片路径+图片名(图片名为生成的随机数)
        userParam.setHeadPortraitUrl(name);
        //把id设置到user参数对象中
        userParam.setId(id);
        userService.updateUser(userParam);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
        super.safeJsonPrint(response, json);
    }

    /**
     * 查询用户信息
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("/selectUser")
    public void selectUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //从cookie中获取他的user对象的id
        Integer id=this.getId(request);
        //如果获取不到,参数异常
        if (id==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        //通过id找出用户对象
        //id,nickname,birthday,sex,mobile,currency,career,head_portrait_url
        UserBO userBO=userService.selectById(id);
        Map<String,Object> mapUser=new HashMap<String, Object>();
        //通过生日得到用户年龄
        Integer age= AgeUtil.getAge(DateUtil.getDate(userBO.getBirthday()));
        //把年龄和用户信息封装到map中返回
        //id,nickname,birthday,sex,mobile,currency,career,head_portrait_url
        //用户id,昵称,生日,性别,手机号,咖豆,职业兴趣,头像路径
        mapUser.put("id", userBO.getId());
        mapUser.put("nickname", userBO.getNickname());
        mapUser.put("age",age);
        mapUser.put("birthday", userBO.getBirthday());
        mapUser.put("sex", userBO.getSex());
        mapUser.put("mobile", userBO.getMobile());
        mapUser.put("currency", userBO.getCurrency());
        mapUser.put("career", userBO.getCareer());
        mapUser.put("integral", userBO.getIntegral());
        mapUser.put("head_portrait_url", userBO.getHeadPortraitUrl());
        //根据需求追加....
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
        super.safeJsonPrint(response, json);
    }

}
