package com.xmcc.wechatorder.common;

import lombok.Getter;

@Getter
public enum ResultEnums {

    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    PARAM_ERROR(1,"参数异常");

    private int code;
    private String msg;

    ResultEnums(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
