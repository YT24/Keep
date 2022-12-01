package com.spi;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ImageHello implements HelloSPI {




    @Override
    public void sayHello() {

        System.out.println("Image Hello");
    }
}