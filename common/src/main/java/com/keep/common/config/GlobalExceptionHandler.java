package com.keep.common.config;

import com.keep.common.expection.CustomExpection;
import com.keep.common.entity.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 全局异常处理
 *
 **/
@ControllerAdvice
class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @ExceptionHandler(value = CustomExpection.class)
    public ResponseResult<Object> handleBizException(CustomExpection ex) {
        logger.error("系统内部错误，业务异常",ex);
        return ResponseResult.fail(ResponseResult.ReturnCode.ERROR,ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseResult<Object> handlerException(Exception ex) {
        logger.error("系统内部错误,服务器繁忙，请稍后重试: ", ex);
        return new ResponseResult(500,"系统错误！！！",ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<ObjectError> errors = result.getAllErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : errors) {
            String message = error.getDefaultMessage();
            stringBuilder.append(message).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        logger.error("系统内部错误，方法参数校验错误:{} ", stringBuilder.toString());
        return ResponseResult.fail(stringBuilder.toString());
    }

    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseResult<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.error("系统内部错误，参数不匹配: ", ex);
        return ResponseResult.fail("参数不匹配");
    }

    @ResponseBody
    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseResult<Object> handleDuplicateKeyException(DuplicateKeyException ex) {
        logger.error("系统内部错误，存在重复数据: ", ex);
        return ResponseResult.fail("存在重复数据");
    }



}