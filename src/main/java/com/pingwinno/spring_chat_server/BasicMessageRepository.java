package com.pingwinno.spring_chat_server;

import com.pingwinno.spring_chat_server.models.MessageModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;

@Component("messageRepository")
public class BasicMessageRepository implements MessageRepository {

    private static final int BUFFER_SIZE = 20;
    private static LinkedList<MessageModel> buffer = new LinkedList<>();

    public BasicMessageRepository() {
        MessageModel messageModel = new MessageModel();
        messageModel.setTime(Date.from(Instant.now()));
        messageModel.setUser("Server");
        messageModel.setMessage("Hello");
        buffer.add(messageModel);
    }

    synchronized public void addMessage(MessageModel message) {
        if (buffer.size() > BUFFER_SIZE) buffer.removeFirst();
        buffer.add(message);
    }

    synchronized public LinkedList<MessageModel> getMessages() {
        return new LinkedList<>(buffer);
    }

}
