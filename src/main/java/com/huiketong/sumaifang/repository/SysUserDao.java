package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao extends JpaRepository<SysUser,Integer> {

    @Override
    <S extends SysUser> S save(S s);

    SysUser findSysUserByUserId(String userName);
}
