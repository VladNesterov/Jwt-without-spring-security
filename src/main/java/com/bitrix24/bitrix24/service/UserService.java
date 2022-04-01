package com.bitrix24.bitrix24.service;

import com.bitrix24.bitrix24.dto.AuthRequest;
import com.bitrix24.bitrix24.entity.UserEntity;
import com.bitrix24.bitrix24.exception.UserException;
import com.bitrix24.bitrix24.repsitory.UserRepository;
import com.bitrix24.bitrix24.util.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Service
public class UserService {
    Base64.Encoder encoder = Base64.getEncoder();
    Base64.Decoder decoder = Base64.getDecoder();
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void UserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        userRepository.save(userEntity);
    }

    public UserEntity loginUser(AuthRequest authRequest) {
        UserEntity dbUserEntity = userRepository.findUserByLogin(authRequest.getName());
        if (dbUserEntity == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setLogin(authRequest.getName());
//          Пароли нельзя хранить в открытом виде в базе
            userEntity.setPassword(encoder.encodeToString(authRequest.getPassword().getBytes()));
//          Наш Secret key
            userEntity.setUuid(UUID.randomUUID().toString());
            return userRepository.save(userEntity);
        } else {
            try {
                if (authRequest.getPassword().equals(new String(decoder.decode(dbUserEntity.getPassword()), "UTF-8"))) {
                    return dbUserEntity;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            throw new UserException("Неправильный пароль");
        }
    }

    public UserEntity getUserByName(String login){
        return userRepository.findUserByLogin(login);
    }
}
