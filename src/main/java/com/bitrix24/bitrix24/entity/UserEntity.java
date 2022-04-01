package com.bitrix24.bitrix24.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name="login")
    private String login;
    @Column(name="password")
    private String password;
    @Column(name="UUID")
    private String uuid;

}
