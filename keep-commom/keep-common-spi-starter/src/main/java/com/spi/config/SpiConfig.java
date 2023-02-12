package com.spi.config;


import com.spi.hello.ImageHello;
import com.spi.hello.TextHello;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpiConfig {


    @ConditionalOnMissingBean(ImageHello.class)
    @Bean
    public ImageHello getImageHello(){

        return new ImageHello();
    }

    @ConditionalOnMissingBean(TextHello.class)
    @Bean
    public TextHello geTextHello(){

        return new TextHello();
    }

}
