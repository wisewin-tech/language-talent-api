package com.wisewin.api.web.controller;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.entity.bo.CertificateResultBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.bo.common.constants.SysConstants;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.UserParam;
import com.wisewin.api.service.CertificateService;
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
import java.util.*;

/**
 * @author Shibo Sun
 * 主机controller
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseCotroller {

    @Resource
    private UserService userService;
    @Resource
    private CertificateService certificateService;


    /**
     * 手机号格式判断
     */
    private boolean phoneFormt(String phone, HttpServletResponse response) {
        if (!(AccountValidatorUtil.isMobile(phone))) {
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
     *
     * @param response
     * @param userBO
     */
    private void putUser(HttpServletResponse response, UserBO userBO) {
        String uuid = UUID.randomUUID().toString();
        System.out.println("UUID:" + uuid);
        super.putLoginUser(uuid, userBO);
        super.setCookie(response, SysConstants.CURRENT_LOGIN_CLIENT_ID, uuid, 24 * 60 * 60 * 30);

//        Object oldKey =RedissonHandler.getInstance().get(userBO.getId() + SysConstants.LOGIN_IDENTIFICATION);
//        if(oldKey!=null){
//            RedissonHandler.getInstance().delete((String)oldKey);
//        }
//        RedissonHandler.getInstance().set(userBO.getId()+ SysConstants.LOGIN_IDENTIFICATION,
//                super.createKey(uuid, com.wisewin.api.common.constants.SysConstants.CURRENT_LOGIN_USER) , (long)24*60*60*30);


    }


    /**
     * 发送验证码
     * 登陆注册
     * 修改绑定
     * @param phone
     */
    @RequestMapping("/send")
    public void send(String phone,String type, HttpServletResponse response) {
        //手机号非空+格式判断
        if (this.phoneFormt(phone, response)) {
            //获取缓存中的失效验证码
            String mobileAuthCode = RedissonHandler.getInstance().get(phone + UserConstants.VERIFY_LOSE.getValue());
            if (mobileAuthCode != null) {
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000012"));
                super.safeJsonPrint(response, json);
                return;
            }
            String count = RedissonHandler.getInstance().get(phone + UserConstants.DEGREE.getValue());
            if (count != null) {
                int coun = Integer.valueOf(count);
                if (coun >= 20) {
                    String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000025"));
                    super.safeJsonPrint(response, json);
                    return;
                }
            }
            if("amend".equals(type)&&userService.selectByPhone(phone)!=null){
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000058"));
                super.safeJsonPrint(response, json);
                return;
            }

            userService.send(phone);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("短信验证码发送成功!"));
            super.safeJsonPrint(response, json);

        }
    }

    /**
     * 测试专用保存cookie接口
     *
     * @param response
     * @param request
     */
    @RequestMapping("/login")
    public void loginCookie(HttpServletResponse response, HttpServletRequest request) {
        String phone = "18631323025";
        //通过手机号查询表中是否有该用户
        UserBO userBO = userService.selectByPhone(phone);
        Map<String, Object> mapUser = new HashMap<String, Object>();
        if (userBO != null) {
            //islogin 是否为登录, yes 登录
            mapUser.put("islogin", UserConstants.Yes.getValue());
            //user对象存入cookie中
            this.putUser(response, userBO);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
            super.safeJsonPrint(response, json);
        } else {
            //如果表里没有该用户,添加用户手机号,把带有手机号的user对象存入cookie中,登录成功,
            UserBO user = userService.selectById(26);
            //islogin 是否为登录, yes 登录
            mapUser.put("islogin", UserConstants.No.getValue());
            //将只带有手机号的user对象存入cookie中
            this.putUser(response, user);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
            super.safeJsonPrint(response, json);
        }
    }


    /**
     * 手机号验证通过,对比用户验证码,修改绑定手机号
     *
     * @param phone
     * @param verify 用户验证码
     */
    @RequestMapping("/updatePhone")
    public void updatePhone(String phone, String verify, HttpServletResponse response, HttpServletRequest request) {
        //手机号非空+格式判断
        this.phoneFormt(phone, response);
        //用户传参非空判断
        if (StringUtils.isEmpty(verify)) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        //获取Redis中的用户验证码
        String mobileAuthCode = RedissonHandler.getInstance().get(phone + UserConstants.VERIFY.getValue());
        System.out.println(mobileAuthCode);
        if (verify.equals(mobileAuthCode)) {
            //获取当前登陆用户
            UserBO loginUser = super.getLoginUser(request);
            Integer id = loginUser.getId();
            boolean bool = userService.updatePhone(id, phone);
            if (bool) {
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
                super.safeJsonPrint(response, json);
            }

        } else {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000011"));
            super.safeJsonPrint(response, json);
        }

    }

    /**
     * 手机号验证通过,对比用户验证码,登录或添加用户信息
     *
     * @param phone
     * @param verify 用户验证码
     */
    @RequestMapping("/register")
    public void register(String phone, String verify, HttpServletResponse response, HttpServletRequest request) {
        //手机号非空+格式判断
        this.phoneFormt(phone, response);
        //用户传参非空判断
        if (StringUtils.isEmpty(verify)) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        //获取Redis中的用户验证码
        System.out.println(phone + UserConstants.VERIFY.getValue());
        String mobileAuthCode = RedissonHandler.getInstance().get(phone + UserConstants.VERIFY.getValue());
        Map<String, Object> mapUser = new HashMap<String, Object>();

        //如果和用户收到的验证码相同
        if (verify.equals(mobileAuthCode)) {
            //通过手机号查询表中是否有该用户
            UserBO userBO = userService.selectByPhone(phone);
            if (userBO != null) {
                //islogin 是否为登录, yes 登录
                if (UserConstants.Yes.getValue().equals(userBO.getStatus())) {
                    //状态,是否被拉黑  yes:拉黑,no:账号正常使用
                    String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000014"));
                    super.safeJsonPrint(response, json);
                } else {
                    //user对象存入cookie中
                    this.putUser(response, userBO);
                    mapUser.put("islogin", UserConstants.Yes.getValue());
                    mapUser.put("userId", userBO.getId());

                    String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
                    super.safeJsonPrint(response, json);
                }
            } else { //如果表里没有该用户,添加用户手机号,把带有手机号的user对象存入cookie中,登录成功,
                UserBO userBO1 = new UserBO();
                userBO1.setMobile(phone);
                userService.insertUser(userBO1);
                userBO1 = userService.selectByPhone(phone);
                //islogin 是否为登录, yes 登录
                mapUser.put("islogin", UserConstants.No.getValue());
                mapUser.put("userId", userBO1.getId());
                //将只带有手机号的user对象存入cookie中
                this.putUser(response, userBO1);
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
                super.safeJsonPrint(response, json);
            }
        } else {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000011"));
            super.safeJsonPrint(response, json);
        }
    }

    /**
     * 扣扣and 微信登陆
     * param openid为微信账号 或者qq号
     * status代表微信或者qq
     */
    @RequestMapping("/openidLogin")
    public void openidLogin(HttpServletRequest request, HttpServletResponse response, String openid, String status) {
        //参数异常
        if (openid == null || status == null || openid.equals("") || status.equals("")) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        //获取到和openid绑定的手机号
        String mobile = userService.checkBind(openid, status);

        //手机号是空的代表没有和openid绑定过
        if (mobile == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000059"));
            super.safeJsonPrint(response, json);
            return;
        }
        //返回给前端的数据
        Map<String, Object> mapUser = new HashMap<String, Object>();

        //存入redis的用户信息
        UserBO userBO = userService.selectByPhone(mobile);

        //islogin 是否为登录, yes 登录
        if (UserConstants.Yes.getValue().equals(userBO.getStatus())) {
            //状态,是否被拉黑  yes:拉黑,no:账号正常使用
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000014"));
            super.safeJsonPrint(response, json);
            return;
        } else {
            //user对象存入cookie中
            this.putUser(response, userBO);
            mapUser.put("islogin", UserConstants.Yes.getValue());
            mapUser.put("userId", userBO.getId());
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
            super.safeJsonPrint(response, json);
            return;
        }


    }

    /**
     * 绑定openid 用户注册 登陆
     */
    @RequestMapping("/bindOpenidLogin")
    public void openidLogin(String phone, String verify, String status, String openid, HttpServletRequest request, HttpServletResponse response) {
        //手机号非空+格式判断
        this.phoneFormt(phone, response);
        //用户传参非空判断
        if (StringUtils.isEmpty(verify) || StringUtils.isEmpty(status) || StringUtils.isEmpty(openid)) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        String mobileAuthCode = RedissonHandler.getInstance().get(phone + UserConstants.VERIFY.getValue());
        Map<String, Object> mapUser = new HashMap<String, Object>();

        //如果和用户收到的验证码相同
        if (verify.equals(mobileAuthCode)) {
            //通过手机号查询表中是否有该用户
            UserBO userBO = userService.selectByPhone(phone);
            if (userBO != null) {
                //islogin 是否为登录, yes 登录
                if (UserConstants.Yes.getValue().equals(userBO.getStatus())) {
                    //状态,是否被拉黑  yes:拉黑,no:账号正常使用
                    String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000014"));
                    super.safeJsonPrint(response, json);
                } else {
                    //user对象存入cookie中
                    this.putUser(response, userBO);
                    mapUser.put("islogin", UserConstants.Yes.getValue());
                    mapUser.put("userId", userBO.getId());
                    //绑定openid
                    userService.bindOpenId(phone, status, openid);
                    String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
                    super.safeJsonPrint(response, json);
                }
            } else { //如果表里没有该用户,添加用户手机号,把带有手机号的user对象存入cookie中,登录成功,
                UserBO userBO1 = new UserBO();
                userBO1.setMobile(phone);
                userService.insertUser(userBO1);
                //绑定openid
                userService.bindOpenId(phone, status, openid);
                userBO1 = userService.selectByPhone(phone);

                //islogin 是否为登录, yes 登录
                mapUser.put("islogin", UserConstants.No.getValue());
                mapUser.put("userId", userBO1.getId());
                //将只带有手机号的user对象存入cookie中
                this.putUser(response, userBO1);
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
                super.safeJsonPrint(response, json);
            }

        } else {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000011"));
            super.safeJsonPrint(response, json);
        }


    }

    /**
     * 修改用户信息
     *
     * @param response
     * @param request
     */
    @RequestMapping("/update")
    public void updateUser(HttpServletResponse response, HttpServletRequest request, UserParam userParam) {
        //从cookie中获取他的user对象的id
        Integer id = this.getId(request);
        //如果获取不到,参数异常
        if (id == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
        }
        //如果获取到了,判断user参数不为空
        if (ParamNullUtil.checkObjAllFieldsIsNull(userParam)) {
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
     * 上传头像
     *
     * @param response
     * @param request
     */
    @RequestMapping("/upPicture")
    public void upPicture(HttpServletResponse response, HttpServletRequest request, MultipartFile image, UserParam userParam)
            throws Exception {
        //从cookie中获取他的user对象的id
        Integer id = this.getId(request);
        //如果获取不到,参数异常
        if (id == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        //图片非空判断
        if (image == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        //上传
        String name = ossClientUtil.uploadImg2Oss(image);
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
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("/selectUser")
    public void selectUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //从cookie中获取他的user对象的id
        Integer id = this.getId(request);
        //如果获取不到,参数异常
        if (id == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        //通过id找出用户对象
        //id,nickname,birthday,sex,mobile,currency,career,head_portrait_url
        UserBO userBO = userService.selectById(id);
        Map<String, Object> mapUser = new HashMap<String, Object>();
        //通过生日得到用户年龄
        Integer age = AgeUtil.getAge(DateUtil.getDate(userBO.getBirthday()));
        //把年龄和用户信息封装到map中返回
        //id,nickname,birthday,sex,mobile,currency,career,head_portrait_url
        //用户id,昵称,生日,性别,手机号,咖豆,职业兴趣,头像路径
        mapUser.put("id", userBO.getId());
        mapUser.put("nickname", userBO.getNickname());
        mapUser.put("age", age);
        mapUser.put("birthday", userBO.getBirthday());
        mapUser.put("sex", userBO.getSex());
        mapUser.put("mobile", userBO.getMobile());
        mapUser.put("currency", userBO.getCurrency());
        mapUser.put("occupation", userBO.getOccupation());
        mapUser.put("interest", userBO.getInterest());
        mapUser.put("integral", userBO.getIntegral());
        mapUser.put("head_portrait_url", userBO.getHeadPortraitUrl());
        //根据需求追加....
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mapUser));
        super.safeJsonPrint(response, json);
    }

    /**
     * 查询用户证书信息
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("/selectUserCert")
    public void selectUserMedal(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //从cookie中获取他的user对象的id
        Integer id = this.getId(request);
        //如果获取不到,参数异常
        if (id == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
        }
        //查询用户证书
        List<CertificateResultBO> certificateResultBOS = certificateService.selectUser(id);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(certificateResultBOS));
        super.safeJsonPrint(response, json);

    }

}
