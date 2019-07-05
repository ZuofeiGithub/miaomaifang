package com.huiketong.miaomaifang.repository;

import com.huiketong.miaomaifang.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    @Override
    <S extends User> S save(S s);


    /**
     * 根据uuid查找唯一用户
     * @param uuid
     * @return
     */
    User findUserByUuid(String uuid);
}
