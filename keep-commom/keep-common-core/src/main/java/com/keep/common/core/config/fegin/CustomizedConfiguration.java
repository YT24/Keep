package com.keep.common.core.config.fegin;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;

public class CustomizedConfiguration{

    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        return new UnwrapDecoder(objectMapper);
    }

}