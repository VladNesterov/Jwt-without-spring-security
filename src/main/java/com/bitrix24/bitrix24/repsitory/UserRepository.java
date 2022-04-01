package com.bitrix24.bitrix24.repsitory;

import com.bitrix24.bitrix24.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findUserByLogin(String login);
}




