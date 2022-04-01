package com.bitrix24.bitrix24.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_message")
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "createDateTime")
    private LocalDateTime createDateTime;

    @Column(name = "login")
    private String login;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login", insertable = false, updatable = false)
    private UserEntity userEntity;
}
