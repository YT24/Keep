package com.example.shardingjdbc.controller;

import com.example.shardingjdbc.bean.User;
import com.example.shardingjdbc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UsersController {

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/add")
    public String addGoods(){
        User good = new User();
        good.setUsername("kobe");
        good.setPassword("24");
        good.setStatus(false);
        userMapper.insert(good);

        return "success";
    }
}
