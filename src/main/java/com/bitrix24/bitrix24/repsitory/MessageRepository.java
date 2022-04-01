package com.bitrix24.bitrix24.repsitory;

import com.bitrix24.bitrix24.entity.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<UserMessage, String> {

    @Query(
            value = "SELECT * FROM user_message  WHERE user_message.login = :login ORDER BY user_message.create_date_time desc limit :count",
            nativeQuery = true)
    List<UserMessage> getLastMessages(String login, Integer count);
}
