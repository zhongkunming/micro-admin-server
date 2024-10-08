package com.devkk.micro.framework.common;


/**
 * @author zhongkunming
 */
public interface ResultCode {

    Integer getCode();

    default String getMessage() {
        return "";
    }
}
