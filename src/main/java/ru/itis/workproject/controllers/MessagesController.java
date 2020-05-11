package ru.itis.workproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import ru.itis.workproject.dto.MessageDto;
import ru.itis.workproject.services.MessagesService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/messages")
@PreAuthorize("isAuthenticated()")
public class MessagesController {

    private MessagesService messagesService;

    private static final Map<DeferredResult<List<MessageDto>>, Integer> chatRequests =
            new ConcurrentHashMap<>();


    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<List<MessageDto>> getMessages(@RequestParam int messageIndex) {

        final DeferredResult<List<MessageDto>> deferredResult = new DeferredResult<>(null, Collections.emptyList());
        chatRequests.put(deferredResult, messageIndex);

        deferredResult.onCompletion(() -> chatRequests.remove(deferredResult));

        List<MessageDto> messages = messagesService.getMessages(messageIndex);
        if (!messages.isEmpty()) {
            deferredResult.setResult(messages);
        }

        return deferredResult;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postMessage(@RequestParam String message, @RequestParam String login) {
        MessageDto messageDto = MessageDto.builder()
                .login(login)
                .text(message)
                .build();
        messagesService.saveMessage(messageDto);


        for (Map.Entry<DeferredResult<List<MessageDto>>, Integer> entry : chatRequests.entrySet()) {
            List<MessageDto> messages = messagesService.getMessages(entry.getValue());
            entry.getKey().setResult(messages);
        }
    }
}
