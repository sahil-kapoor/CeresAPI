package com.ceres.domain.repository.message;

import com.ceres.domain.entity.human.Human;
import com.ceres.domain.entity.message.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.SortedSet;

/**
 * Created by leonardo on 25/04/15.
 */
@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findBySender(Human sender);

    List<ChatMessage> findByReceiver(Human receiver);

    SortedSet<ChatMessage> findBySenderAndReceiverOrderByDateAsc(Human sender, Human receiver);

    SortedSet<ChatMessage> findBySenderInAndReceiverIn(List<Human> senderIn, List<Human> receiverIn);

}
