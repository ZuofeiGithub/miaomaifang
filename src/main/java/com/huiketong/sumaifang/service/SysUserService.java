package com.huiketong.sumaifang.service;

public interface SysUserService {
    boolean registerSysUser(String userName,String password);

    boolean login(String username,String password);
}
