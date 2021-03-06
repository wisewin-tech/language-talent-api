package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;
import com.wisewin.api.util.TimeUtil;
import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

public class UserBO  extends BaseModel{

    private Integer id; //用戶表
    private String name; //姓名
    private String nickname; //昵称
    private String password; //密码
    private String email; //邮箱
    private String mobile; //手机号
    private String ageGroup; //年龄段
    private String headPortraitUrl; //头像路径
    private String learningGoal; //学习目的
    private String sex; //性别
    private String birthday; //生日
    private Integer age; //年龄
    private String source; //个人/企业
    private Integer integral=0; //积分
    private Integer currency=0; //咖豆
    private Integer studyingLanguageId; //正在学习的语言id
    private String isLogin; //是否为登录 Yes:登录 No:注册
    private String occupation; //职业
    private String interest; //兴趣
    private String status; //状态,是否被拉黑  yes:拉黑,no:账号正常使用
    private String inviteCode; //邀请码
    private String byInvite; //被邀请码
    private String qqOpenid; //qq登录id
    private String wxOpenid; //微信登录id
    private Integer weekContinuousSign;//本周连续签到天数
    private Integer continuousSign; //连续签到天数
    private Integer cumulativeSign; //累计签到天数
    private Date lastSign; //上次签到时间
    private Integer continuousLearning; //连续学习天数
    private Integer cumulativeLearning; //累计学习天数
    private String studyDate; //上次学习日期
    private Integer createId; //创建人id
    private Integer updateId; //修改人id
    private Date createTime; //创建时间
    private Date updateTime; //修改时间
    private String registeredChannels;//注册渠道

    public String getRegisteredChannels() {
        return registeredChannels;
    }

    public void setRegisteredChannels(String registeredChannels) {
        this.registeredChannels = registeredChannels;
    }

    @Override
    public String toString() {
        return "UserBO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", ageGroup='" + ageGroup + '\'' +
                ", headPortraitUrl='" + headPortraitUrl + '\'' +
                ", learningGoal='" + learningGoal + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", age=" + age +
                ", source='" + source + '\'' +
                ", integral=" + integral +
                ", currency=" + currency +
                ", studyingLanguageId=" + studyingLanguageId +
                ", isLogin='" + isLogin + '\'' +
                ", occupation='" + occupation + '\'' +
                ", interest='" + interest + '\'' +
                ", status='" + status + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", byInvite='" + byInvite + '\'' +
                ", qqOpenid='" + qqOpenid + '\'' +
                ", wxOpenid='" + wxOpenid + '\'' +
                ", weekContinuousSign=" + weekContinuousSign +
                ", continuousSign=" + continuousSign +
                ", cumulativeSign=" + cumulativeSign +
                ", lastSign=" + lastSign +
                ", continuousLearning=" + continuousLearning +
                ", cumulativeLearning=" + cumulativeLearning +
                ", studyDate='" + studyDate + '\'' +
                ", createId=" + createId +
                ", updateId=" + updateId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public UserBO() {
    }

    public UserBO(Integer id, String name, String nickname, String mobile, String sex, String birthday) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.mobile = mobile;
        this.sex = sex;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public String getLearningGoal() {
        return learningGoal;
    }

    public void setLearningGoal(String learningGoal) {
        this.learningGoal = learningGoal;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getStudyingLanguageId() {
        return studyingLanguageId;
    }

    public void setStudyingLanguageId(Integer studyingLanguageId) {
        this.studyingLanguageId = studyingLanguageId;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getByInvite() {
        return byInvite;
    }

    public void setByInvite(String byInvite) {
        this.byInvite = byInvite;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public Integer getContinuousSign() {
        return continuousSign;
    }

    public void setContinuousSign(Integer continuousSign) {
        this.continuousSign = continuousSign;
    }

    public Integer getCumulativeSign() {
        return cumulativeSign;
    }

    public void setCumulativeSign(Integer cumulativeSign) {
        this.cumulativeSign = cumulativeSign;
    }

    public Date getLastSign() {
        return lastSign;
    }

    public void setLastSign(Date lastSign) {
        this.lastSign = lastSign;
    }

    public Integer getContinuousLearning() {
        return continuousLearning;
    }

    public void setContinuousLearning(Integer continuousLearning) {
        this.continuousLearning = continuousLearning;
    }

    public Integer getCumulativeLearning() {
        return cumulativeLearning;
    }

    public void setCumulativeLearning(Integer cumulativeLearning) {
        this.cumulativeLearning = cumulativeLearning;
    }

    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getWeekContinuousSign() {
        return weekContinuousSign;
    }

    public void setWeekContinuousSign(Integer weekContinuousSign) {
        this.weekContinuousSign = weekContinuousSign;
    }
}
