package ru.itis.workproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.itis.workproject.dto.MessageDto;
import ru.itis.workproject.models.Message;
import ru.itis.workproject.repositories.MessagesRepository;

import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public void saveMessage(MessageDto messageDto) {
        messagesRepository.save(Message.builder()
                .login(messageDto.getLogin())
                .text(messageDto.getText())
                .build());
    }

    @Override
    public List<MessageDto> getMessages(int messageIndex) {
        List<MessageDto> messagesDto = MessageDto.from(messagesRepository.findAll());
        Assert.isTrue((messageIndex >= 0) && (messageIndex <= messagesDto.size()), "Invalid message index");
        return messagesDto.subList(messageIndex, messagesDto.size());
    }
}
