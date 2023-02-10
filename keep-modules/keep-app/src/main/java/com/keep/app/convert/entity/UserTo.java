package com.keep.app.convert.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserTo {

    private String name;

    private String age;

    public UserTo(){
        System.out.println("UserTo Autwired");
    }

}
