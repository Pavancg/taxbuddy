package com.taxbuddy.taxbuddy_ai_chat_service.dto;

import jakarta.validation.constraints.NotNull;

public record SessionCreateRequest(
        @NotNull(message = "User Id is required")
        Long userId
) {
}
