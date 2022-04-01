package com.bitrix24.bitrix24.service;

import com.bitrix24.bitrix24.dto.AuthRequest;
import com.bitrix24.bitrix24.dto.AuthResponse;
import com.bitrix24.bitrix24.entity.UserEntity;
import com.bitrix24.bitrix24.exception.JwtException;
import io.jsonwebtoken.*;
import lombok.NonNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTService {

    @Autowired
    UserService userService;

    public AuthResponse getAccessToken(@NonNull AuthRequest user) {
//      Создаем и гененируем токен при помощи данных пользователя, использую uuid и его имя
        return AuthResponse.builder()
                .token(generateAccessToken(user.getName(), userService.loginUser(user).getUuid())).build();
    }

    private String generateAccessToken(String login, String secret) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .claim("name:", login)
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public void validationJwt(String requestJwt) {
//      Декодируем Jwt, достаем из него имя пользователя и ищем его в базе
        String[] chunks = requestJwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject jsonObject = new JSONObject(payload);
        UserEntity userEntity = userService.getUserByName(jsonObject.get("name:").toString());
        if (userEntity==null){
            throw new JwtException("Нет такого пользователя");
        }
        try {
//            При неверном secret key(uuid) пользователя выдаст SignatureException,при неправильном формате IllegalArgumentException
            Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(userEntity.getUuid()))
                    .parseClaimsJws(requestJwt).getBody();
        }catch (IllegalArgumentException | SignatureException e){
            e.printStackTrace();
            throw new JwtException("Неверный токен");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
