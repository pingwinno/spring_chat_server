package com.pingwinno.spring_chat_server;

import com.pingwinno.spring_chat_server.models.MessageModel;

import java.util.LinkedList;

public interface MessageRepository {
    void addMessage(MessageModel message);

    LinkedList<MessageModel> getMessages();
}
