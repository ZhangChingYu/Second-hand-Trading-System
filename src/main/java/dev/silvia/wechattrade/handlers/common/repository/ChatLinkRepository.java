package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.ChatLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLinkRepository extends JpaRepository<ChatLink,String> {
    ChatLink findByFromIdAndToId(String toUser, String fromUser);
}
