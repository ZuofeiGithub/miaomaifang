package com.huiketong.sumaifang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewPageController {
    @GetMapping("/login")
    public String login(){
        return "/login/login";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin/admin";
    }

    @GetMapping("/main")
    public String main(){
        return "main/main";
    }
}
