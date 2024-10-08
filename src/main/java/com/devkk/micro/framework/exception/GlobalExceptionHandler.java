package com.devkk.micro.framework.exception;

import com.devkk.micro.framework.common.GlobalResultCode;
import com.devkk.micro.framework.common.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

/**
 * @author zhongkunming
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handlerBusinessException(BusinessException e) {
        log.error("ExceptionHandler.handlerBusinessException, ", e);
        return Result.error(e.getResult(), e.getObjs());
    }

    @ExceptionHandler(SystemException.class)
    public Result<Void> handleSystemException(SystemException e) {
        log.error("ExceptionHandler.handleSystemException, ", e);
        return Result.error(e.getResult(), e.getObjs());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handlerMethodValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.error("ExceptionHandler.handlerMethodValidationException, ", e);
        return Result.error(GlobalResultCode.METHOD_ARGUMENT_VALID_NOT_PASS, errorMessage);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public Result<Void> handlerHandlerMethodValidationException(HandlerMethodValidationException e) {
        String errorMessage = e.getAllValidationResults()
                .stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.error("ExceptionHandler.handlerHandlerMethodValidationException, ", e);
        return Result.error(GlobalResultCode.HANDLER_METHOD_VALID_NOT_PASS, errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handlerConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        log.error("ExceptionHandler.handlerConstraintViolationException, ", e);
        return Result.error(GlobalResultCode.CONSTRAINT_VALID_NOT_PASS, errorMessage);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Void> handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("ExceptionHandler.handlerMissingServletRequestParameterException, ", e);
        return Result.error(GlobalResultCode.MISS_PARAMETER_VALID_NOT_PASS);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Result<Void> handlerNoResourceFoundException(NoResourceFoundException e) {
        log.error("ExceptionHandler.handlerNoResourceFoundException, ", e);
        return Result.error(GlobalResultCode.RESOURCE_NOT_FOUND_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("ExceptionHandler.handleException, ", e);
        return Result.error(GlobalResultCode.ERROR);
    }
}
