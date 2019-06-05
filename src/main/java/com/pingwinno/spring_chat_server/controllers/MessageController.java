package com.pingwinno.spring_chat_server.controllers;

import com.pingwinno.spring_chat_server.MessageRepository;
import com.pingwinno.spring_chat_server.models.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.LinkedList;

@Controller
public class MessageController {
    private MessageRepository messageRepository;
    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageRepository getMessageRepository() {
        return messageRepository;
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @MessageMapping("/usersMessage")
    @SendTo("/topic/broadcast")
    public MessageModel[] handleMessage(@Payload MessageModel message) {
        logger.debug("Message received.");
        logger.trace("Message received. Content: {}", message);
        messageRepository.addMessage(message);
        return new MessageModel[]{message};
    }

    @SubscribeMapping("/topic/broadcast")
    public LinkedList<MessageModel> handleNewClient() {
        logger.debug("User connected.");

        return messageRepository.getMessages();
    }

}
