package com.ceres.service.message;

import com.ceres.domain.entity.message.ChatMessage;

import java.util.List;

/**
 * Created by leonardo on 25/04/15.
 */
public interface MessageService {

    List<ChatMessage> getBySender();

    List<ChatMessage> getByReceiver();
}
