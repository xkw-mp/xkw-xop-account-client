/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.account.client.response;

/**
 * ActiveAccountErrorEnum
 * 账号激活active_result枚举
 * 这里仅列出常见枚举，更多错误码参考具体说明
 *
 * @author LiuJibin
 */
public enum ActiveAccountErrorEnum {

    /**
     * 激活成功
     */
    SUCCESS(0, "激活成功"),
    /**
     * 激活中，之后会通过回调URI进行通知
     */
    ACTIVATING(1, "激活中，请等待通知或稍后查询"),
    /**
     * 未激活，可能是完全未激活，也可能在激活中
     */
    NOT_ACTIVATED(2, "设备未激活，如已调用激活接口，请等待激活成功或再次激活"),
    /**
     * 激活错误，具体错误信息请联系XOP技术支持
     */
    ACTIVE_ERROR(20, "激活失败，请重试或联系XOP技术支持"),
    /**
     * 账号包/权益包用尽
     */
    PACKAGE_RAN_OUT(21, "权益包/账号包已用完，请联系XOP平台进行购买");

    private final Integer code;
    private final String msg;

    ActiveAccountErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 根据code生成ErrorEnum，无则为null
     * @param code active_result
     * @return ActiveAccountErrorEnum
     */
    public static ActiveAccountErrorEnum fromCode(Integer code) {
        for (ActiveAccountErrorEnum errorEnum : values()) {
            if (errorEnum.getCode().equals(code)) {
                return errorEnum;
            }
        }
        return null;
    }

    public static boolean isSuccess(Integer code) {
        return SUCCESS.getCode().equals(code) || ACTIVATING.getCode().equals(code);
    }

}
