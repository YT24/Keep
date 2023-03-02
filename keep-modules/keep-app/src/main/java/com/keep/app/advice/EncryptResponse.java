package com.keep.app.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keep.app.annotation.Encrypt;
import com.keep.app.config.EncryptProperties;
import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.common.core.utils.AESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@EnableConfigurationProperties(EncryptProperties.class)
//@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<ResponseResult> {

    private ObjectMapper om = new ObjectMapper();

    @Autowired
    EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public ResponseResult beforeBodyWrite(ResponseResult body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        byte[] keyBytes = encryptProperties.getKey().getBytes();
        try {
            if (body.getMsg()!=null) {
                body.setMsg(AESUtils.encrypt(body.getMsg().getBytes(),keyBytes));
            }
            if (body.getData() != null) {
                body.setResponseData(AESUtils.encrypt(om.writeValueAsBytes(body.getData()), keyBytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
}