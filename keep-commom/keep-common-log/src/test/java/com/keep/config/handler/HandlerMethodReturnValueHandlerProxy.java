package com.keep.config.handler;

import brave.Tracer;
import com.keep.common.core.domain.entity.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class HandlerMethodReturnValueHandlerProxy implements HandlerMethodReturnValueHandler {

    private HandlerMethodReturnValueHandler proxyObject;
    private Tracer trace;

    public HandlerMethodReturnValueHandlerProxy(HandlerMethodReturnValueHandler proxyObject, Tracer trace) {
        this.proxyObject = proxyObject;
        this.trace = trace;
    }
    /**
     * 不修改原始的支持类型
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return proxyObject.supportsReturnType(returnType);
    }

    /**
     * 添加 traceId
     *
     */
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        if (returnValue instanceof ResponseResult) {
            String traceIdString = trace.currentSpan().context().traceIdString();
            if (StringUtils.isNotEmpty(traceIdString)) {
                ((ResponseResult) returnValue).setTraceId(traceIdString);
            }
        }

        proxyObject.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}