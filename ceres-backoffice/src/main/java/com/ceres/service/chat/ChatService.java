package com.ceres.service.chat;

import com.ceres.controller.v1.views.ChatView;
import com.ceres.domain.entity.human.Human;
import com.ceres.domain.entity.message.ChatMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by leonardo on 25/04/15.
 */
public interface ChatService {
    Map<Long, String> getConversationsOf(Human p);

    List<ChatView> getChatMessages(Human p, Human n);

    ChatMessage registryMessage(Long senderId, Long receiverId, String message);
}
