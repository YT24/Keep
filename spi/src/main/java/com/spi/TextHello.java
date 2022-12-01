package com.spi;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TextHello implements HelloSPI {

    @Override
    public void sayHello() {
        System.out.println("Text Hello");
    }
}
