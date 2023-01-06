package com.spi.hello;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ImageHello implements HelloSPI {




    @Override
    public void sayHello() {

        System.out.println("Image Hello");
    }
}