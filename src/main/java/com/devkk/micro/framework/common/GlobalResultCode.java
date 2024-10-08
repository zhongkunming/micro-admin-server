package com.devkk.micro.framework.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum GlobalResultCode implements ResultCode {
    SUCCESS(1000, "请求成功"),

    METHOD_ARGUMENT_VALID_NOT_PASS(1001, "参数验证未通过：{}"),
    HANDLER_METHOD_VALID_NOT_PASS(1002, "参数验证未通过：{}"),
    CONSTRAINT_VALID_NOT_PASS(1003, "参数验证未通过：{}"),
    MISS_PARAMETER_VALID_NOT_PASS(1004, "参数验证未通过"),
    RESOURCE_NOT_FOUND_ERROR(1005, "资源不存在"),

    ILLEGALITY_TOKEN_ERROR(1100, "非法Token"),
    LOGIN_EXPIRED_ERROR(1101, "登录已过期，请重新登录"),

    ERROR(9999, "系统错误，请联系管理员");

    private final Integer code;

    private final String message;
}
