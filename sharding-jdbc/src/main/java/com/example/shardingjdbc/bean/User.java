package com.example.shardingjdbc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Id;


@Data
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private boolean status;

}
