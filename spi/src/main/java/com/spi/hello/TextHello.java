package com.spi.hello;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TextHello implements HelloSPI {

    @Override
    public void sayHello() {
        System.out.println("Text Hello");
    }
}
