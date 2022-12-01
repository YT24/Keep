package com.example.shardingjdbc.bean;

import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Data;


@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private boolean status;

}
