package com.taxbuddy.taxbuddy_ai_chat_service.repository;

import com.taxbuddy.taxbuddy_ai_chat_service.entity.ChatMessage;
import com.taxbuddy.taxbuddy_ai_chat_service.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ChatMessageRepository

        extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatSessionOrderByCreatedAtAsc(

            ChatSession chatSession);

    List<ChatMessage> findAllByChatSessionSessionId(

            String sessionId);

    long countByChatSession(ChatSession chatSession);

}

