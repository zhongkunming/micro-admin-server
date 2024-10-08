package com.devkk.micro.framework.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zhongkunming
 */
@Slf4j
@Data
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -8586054880473556418L;

    @Schema(title = "响应码")
    private Integer code;

    @Schema(title = "响应消息")
    private String message;

    @Schema(title = "响应结果")
    private T data;

    private Result() {
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = GlobalResultCode.SUCCESS.getCode();
        result.message = GlobalResultCode.SUCCESS.getMessage();
        result.data = data;
        return result;
    }

    public static <T> Result<T> error() {
        return error(GlobalResultCode.ERROR);
    }

    public static <T> Result<T> error(ResultCode resultCode, Object... objs) {
        Result<T> result = new Result<>();
        result.code = resultCode.getCode();
        result.message = StrUtil.format(resultCode.getMessage(), objs);
        return result;
    }
}
