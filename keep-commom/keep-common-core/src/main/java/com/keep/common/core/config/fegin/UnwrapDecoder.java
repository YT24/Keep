package com.keep.common.core.config.fegin;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.common.core.expection.BizExpection;
import feign.Response;
import feign.codec.Decoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Objects;

@RequiredArgsConstructor
public class UnwrapDecoder implements Decoder {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Object decode(Response response, Type type) {
        Reader reader = response.body().asReader(Charset.defaultCharset());
        ResponseResult<?> result = objectMapper.readValue(reader, ResponseResult.class);
        // 根据 code 判断操作是否成功
        if (Objects.equals(HttpStatus.OK.value(), result.getCode())) {
            Object data = result.getData();
            JavaType javaType = TypeFactory.defaultInstance().constructType(type);
            return objectMapper.convertValue(data, javaType);
        }
        // 若不成功，抛出业务异常，注意此处的异常会在 DecodeException 中被捕获，后文会处理
        throw new BizExpection(result.getMsg());
    }
}
