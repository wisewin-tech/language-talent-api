package com.wisewin.api.common.constants;

public enum UserConstants {

        /*  验证码标识` */
        VERIFY("verify"),
        /*  积分  */
        Integral("积分"),
        /*  咖豆  */

        /*  增加  */
        Increase("获取"),
        /*  减少  */
        Decrease("支出"),
        /*  签到增加积分值` */
        signNum(10);

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