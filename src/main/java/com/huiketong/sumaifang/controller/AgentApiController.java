package com.huiketong.sumaifang.controller;

import com.huiketong.sumaifang.vo.BaseResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/agentapi")
public class AgentApiController {
    @PostMapping(value = "/wxlogin")
    public BaseResp wxLogin(String code){
        BaseResp resp = new BaseResp();
        return resp;
    }
}
