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
        /*  签到增加积分值 */  SIGNNUM("sign_integral");


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