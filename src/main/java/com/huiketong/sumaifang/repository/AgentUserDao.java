package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.AgentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface AgentUserDao extends JpaRepository<AgentUser, Integer> {
    @Override
    <S extends AgentUser> S save(S s);


    @Override
    long count();

    AgentUser findAgentUserById(Integer id);

    AgentUser findAgentUserByOpenid(String openId);

    /**
     * 更新用户的token
     */
    @Query(value = "update agent_user set token = ?1 where openid = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    void updatToken(String token, String openid);

    AgentUser findAgentUserByToken(String token);

    @Query(value = "update agent_user set city_name = ?1,user_name = ?2,user_phone = ?3,register_time= ?6,company_info = ?4,isbind = ?7 where token = ?5", nativeQuery = true)
    @Modifying
    @Transactional
    void updateUserInfo(String city_name, String user_name, String user_telphone, String company, String token, Date registerTime,Integer isbind);

    @Query(value = "select * from agent_user where user_phone = ?1 and token = ?2",nativeQuery = true)
    AgentUser findRegisterUser(String user_telphone, String token);

    @Query(value = "update agent_user set isbind = ?1 where token = ?2 and user_phone = ?3",nativeQuery = true)
    @Modifying
    @Transactional
    void updateBindStatus(int status,String token,String phone);
}
