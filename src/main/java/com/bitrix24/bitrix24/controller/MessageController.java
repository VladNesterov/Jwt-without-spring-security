package com.bitrix24.bitrix24.controller;

import com.bitrix24.bitrix24.dto.MessageDto;
import com.bitrix24.bitrix24.service.JWTService;
import com.bitrix24.bitrix24.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;
    @Autowired
    JWTService JWTService;

    @RequestMapping(value = "/save/message", method = RequestMethod.POST)
    public MessageDto saveMessage(@RequestBody MessageDto messageDto, @RequestHeader (name="Bearer") String token) {
        JWTService.validationJwt(token);
        return messageService.saveMessage(messageDto);
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public List<String> getMessage(@RequestBody MessageDto messageDto, @RequestHeader (name="Bearer") String token) {
        JWTService.validationJwt(token);
        return messageService.getMessages(messageDto);
    }
}
