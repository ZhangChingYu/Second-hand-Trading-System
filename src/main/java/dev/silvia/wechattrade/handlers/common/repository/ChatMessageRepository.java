package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    ChatMessage findByNumberAndIsLatest(String linkId, Integer isLatest);

    List<ChatMessage> findByNumber(String linkId);
}
