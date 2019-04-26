package com.wisewin.api.common.constants;

public enum UserConstants {
        /* 管理员用户信息 */
        MAN("man"),WOMEN("women"),

        /*  管理员状态 */
        NORMAL("normal"),LOGOUT("logout"),

        /*  管理员标识 */
        YES("yes"),NO("no"),
        /*  验证码标识` */
        VERIFY("verify");

    private UserConstants(String value) {
        this.value = value;
    }
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }




}
