package com.ceres.controller.v1.views;

import com.ceres.domain.entity.message.ChatMessage;

/**
 * Created by leonardo on 25/04/15.
 */
public class ChatView {

    private Long sender;

    private Long receiver;

    private ChatMessage message;

    public ChatView(Long id, Long id1, ChatMessage k) {
        this.sender = id;
        this.receiver = id1;
        this.message = k;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
