package com.keep.app.desginPattern.factory_filter;

public enum LoginFilterEnum {

    USER_PWD(0, "用户名密码过滤校验");

    private final int code;
    private final String desc;

    LoginFilterEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LoginFilterEnum getByCode(int code){
        for (LoginFilterEnum value : LoginFilterEnum.values()) {
            if(value.code == code){
                return value;
            }
        }
        return null;
    }
}
