package com.taxbuddy.taxbuddy_ai_chat_service.dto;

import com.taxbuddy.taxbuddy_ai_chat_service.entity.SessionStatus;
import software.amazon.awssdk.services.bedrockruntime.endpoints.internal.Value;

import java.time.LocalDateTime;

public record SessionResponse(
        String sessionId,
        Long userId,
        SessionStatus status,
        LocalDateTime createdAt,
        String message
) {

    public static SessionResponse created(String sessionId, Long userId){
        return new SessionResponse(sessionId, userId, SessionStatus.ACTIVE, LocalDateTime.now(), "Session created successfully");
    }
}
