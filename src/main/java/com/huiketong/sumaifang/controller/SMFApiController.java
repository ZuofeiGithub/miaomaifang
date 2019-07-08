package com.huiketong.sumaifang.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class SMFApiController {

    /**
     * 用户注册
     * @return
     */
    @PostMapping(value = "/register")
    public boolean register(String city_name,String user_name,String user_type,String agree){

        return false;
    }
}
