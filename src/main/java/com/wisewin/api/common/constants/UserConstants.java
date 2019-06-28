package com.wisewin.api.common.constants;

public enum UserConstants {

        /*  验证码标识` */  VERIFY("verify"),
        /*  手机发送验证码记录  */RECORD("record"),
        /*  验证码失效标识` */ VERIFY_LOSE("verify_lose"),
        /*  发送验证码次数` */ DEGREE("degree"),
        /*  积分  */  INTEGRAL("积分"),
        /*  咖豆  */  CURRENCY("咖豆"),
        /*  课时  */  CHAPTER("hour"),
        /*  发现  */  DISCOVERY("discover"),
        /*  发现  */  SUBJECT("subject"),
        /*  Yes  */  Yes("yes"),
        /*  No  */  No("no"),
        /*  增加  */ INCREASE("获取"),
        /*  减少  */  DECREASE("支出"),
        /*  积分兑换咖豆比例 */  SCALE("integral_proportion"),
        /*  签到增加积分值 */  SIGNNUM("sign_integral"),
        /*  邀请好友获取咖豆*/ INVITER_MSG("邀请好友获取咖豆"),
        /*  邀请别人获取咖豆数量 */ INVITER("inviter"),
        /*  被邀请人获取咖豆 */ BYINVITER("by_inviter"),
        /*  学前热身 */         WARM("warm"),
        /*  学前热身每天前 x 次送积分 */         WARM_UP("warm_up"),
        /*  学前热身送积分(y  个 积分) */         WARM_UP_INVITER("warm_up_inviter"),
        /* 观看课时视频 */         WATCH("watch"),
        /*  观看课时视频前 values 次送积分 */         VIDEO("video"),
        /* 观看视频送积分(values 个 积分 )*/         VIDEO_INVITER("video_inviter"),
        /* 课后测试  */         AFTER("after"),
        /* 完成课后测试前 values 次送积分*/         TEST("test"),
        /* 完成课后测试送积分(values 个积分)*/         TEST_INVITER("test_inviter"),
        /* 完成学前热身获得积分 */         COMPLETE_WARM("完成学前热身获取积分"),
        /* 完成教学视频 */         COMPLETE_VODE("完成教学视频获取积分"),
        /* 完成课后测试 */         COMPLETE_TEST("完成课后测试获取积分");

    private UserConstants(String value) {
        this.value = value;
    }

    private UserConstants(Integer num) {
        this.num = num;
    }

    private String value;
    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }




}