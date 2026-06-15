package com.taxbuddy.taxbuddy_ai_chat_service.repository;


import com.taxbuddy.taxbuddy_ai_chat_service.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

import java.util.Optional;

@Repository

public interface ChatSessionRepository

        extends JpaRepository<ChatSession, Long> {

    Optional<ChatSession> findBySessionId(String sessionId);

    List<ChatSession> findAllByUserId(Long userId);

    List<ChatSession> findAllByUserIdAndStatus(

            Long userId, SessionStatus status);

    boolean existsBySessionId(String sessionId);

}

