package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.ChatList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatListRepository extends JpaRepository<ChatList, String> {
    ChatList findByNumberAndFromIdAndToId(String linkId, String toUser, String fromUser);
}
