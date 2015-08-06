package com.ceres.service.chat;

import com.ceres.controller.v1.views.ChatView;
import com.ceres.domain.entity.human.Human;
import com.ceres.domain.entity.message.ChatMessage;
import com.ceres.domain.repository.human.HumanRepository;
import com.ceres.domain.repository.message.ChatRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by leonardo on 25/04/15.
 */
@Service
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;

    private HumanRepository humanRepository;

    @Inject
    public ChatServiceImpl(ChatRepository chatRepository, HumanRepository humanRepository) {
        this.chatRepository = chatRepository;
        this.humanRepository = humanRepository;
    }

    @Override
    public Map<Long, String> getConversationsOf(Human p) {

        List<ChatMessage> messages = chatRepository.findBySender(p);

        Map<Long, String> conversationsMap = new HashMap<>();

        messages.forEach(k -> {
            conversationsMap.put(k.getReceiver().getId(), k.getReceiver().getName().toString());
        });

        return conversationsMap;
    }

    @Override
    public List<ChatView> getChatMessages(Human p, Human n) {
        SortedSet<ChatMessage> messages = new TreeSet<>();

        List<Human> humans = new ArrayList<>(Arrays.asList(p, n));

        messages.addAll(chatRepository.findBySenderInAndReceiverIn(humans, humans));

        List<ChatView> chatView = new ArrayList<>();

        messages.forEach(k -> {
            chatView.add(new ChatView(k.getReceiver().getId(), k.getSender().getId(), k));
        });

        return chatView;
    }

    @Override
    public ChatMessage registryMessage(Long senderId, Long receiverId, String message) {

        ChatMessage chatMessage;

        Human sender = humanRepository.findOne(senderId);
        Human receiver = humanRepository.findOne(receiverId);

        chatMessage = new ChatMessage(message, receiver, sender);

        return chatRepository.save(chatMessage);
    }
}
