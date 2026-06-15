package com.taxbuddy.taxbuddy_ai_chat_service.dto;

public record ChatResponse(
        String sessionId,
        String message,
        String role,
        boolean escalationRequired,
        String escalationReason
) {
    public static ChatResponse ai(String sessionId, String message){
        return new ChatResponse(sessionId, message, "ASSISTANT", false, null);
    }

    public static ChatResponse escalation(String sessionId, String message, String reason){
        return new ChatResponse(sessionId, message, "ASSISTANT", true, reason);
    }
}
