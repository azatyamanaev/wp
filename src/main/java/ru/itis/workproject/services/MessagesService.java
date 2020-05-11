package ru.itis.workproject.services;

import ru.itis.workproject.dto.MessageDto;

import java.util.List;

public interface MessagesService {
    void saveMessage(MessageDto messageDto);
    List<MessageDto> getMessages(int messageIndex);
}
