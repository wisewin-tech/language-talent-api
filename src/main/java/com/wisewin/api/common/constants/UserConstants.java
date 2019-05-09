package com.wisewin.api.common.constants;

public enum UserConstants {

        /*  验证码标识` */  VERIFY("verify"),
        /*  验证码失效标识` */ VERIFY_LOSE("verify_lose"),
        /*  积分  */  INTEGRAL("积分"),
        /*  咖豆  */  CURRENCY("currency"),
        /*  课时  */  CHAPTER("课时"),
        /*  课时  */  DISCOVERY("发现"),
        /*  Yes  */  Yes("yes"),
        /*  No  */  No("no"),
        /*  增加  */ INCREASE("获取"),
        /*  减少  */  DECREASE("支出"),
        /*  积分兑换咖豆比例 */  SCALE("积分兑换咖豆比例"),
        /*  签到增加积分值 */  SIGNNUM("签到获取积分");


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