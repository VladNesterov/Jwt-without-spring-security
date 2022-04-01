package com.bitrix24.bitrix24.service;

import com.bitrix24.bitrix24.dto.MessageDto;
import com.bitrix24.bitrix24.entity.UserMessage;
import com.bitrix24.bitrix24.repsitory.MessageRepository;
import com.bitrix24.bitrix24.repsitory.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    public MessageDto saveMessage(MessageDto dto) {
        UserMessage userMessage = new UserMessage();
        userMessage.setMessage(dto.getMessage());
        userMessage.setLogin(dto.getName());
        userMessage.setCreateDateTime(LocalDateTime.now());
        userMessage.setUserEntity(userRepository.findUserByLogin(dto.getName()));
        return convertEntityMessageToDto(messageRepository.save(userMessage));
    }

    public List<String> getMessages(MessageDto messageDto) {
        List<String> list = Arrays.asList(messageDto.getMessage().split(" "));
        return getLastHistoryCount(messageDto.getName(), Integer.valueOf(list.get(1)));
    }

    private MessageDto convertEntityMessageToDto(UserMessage userMessage) {
        return MessageDto.builder().message(userMessage.getMessage()).name(userMessage.getUserEntity().getLogin()).build();
    }

    private List<String> getLastHistoryCount(String login, Integer count){
        return messageRepository.getLastMessages(login,count)
                .stream()
                .map(UserMessage::getMessage)
                .collect(Collectors.toList());
    }

}
