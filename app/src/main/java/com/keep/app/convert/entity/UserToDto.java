package com.keep.app.convert.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserToDto {

    private String username;

    private String age;

    public UserToDto(){
        System.out.println("UserTo Autwired");
    }

}
