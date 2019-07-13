package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.AgentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentUserDao extends JpaRepository<AgentUser,Integer> {
    @Override
    <S extends AgentUser> S save(S s);


    /**
     * 根据uuid查找唯一用户
     * @param uuid
     * @return
     */
    AgentUser findUserByUuid(String uuid);

    @Override
    long count();

    AgentUser findAgentUserById(Integer id);
}
