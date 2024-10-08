package com.devkk.micro.framework.exception;

import com.devkk.micro.framework.common.ResultCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.text.StrUtil;

/**
 * @author zhongkunming
 */
@Getter
public class SystemException extends RuntimeException {

    private final ResultCode result;

    private final Object[] objs;

    private final String exceptionMessage;

    public SystemException(ResultCode result, Object... objs) {
        super(StrUtil.format(result.getMessage(), objs));
        this.result = result;
        this.objs = objs;
        this.exceptionMessage = super.getMessage();
    }

    public SystemException(Exception e, ResultCode result, Object... objs) {
        super(e);
        this.result = result;
        this.objs = objs;
        this.exceptionMessage = StrUtil.format(result.getMessage(), objs);
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (StringUtils.isBlank(message)) {
            message = exceptionMessage;
        }
        return message;
    }
}
