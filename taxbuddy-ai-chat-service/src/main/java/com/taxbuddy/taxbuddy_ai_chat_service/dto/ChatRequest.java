package com.taxbuddy.taxbuddy_ai_chat_service.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(

        @NotBlank(message = "Session ID is required")
        String sessionId,

        @NotBlank(message = "Message is required")
        String message,

        long userId

) {
}
