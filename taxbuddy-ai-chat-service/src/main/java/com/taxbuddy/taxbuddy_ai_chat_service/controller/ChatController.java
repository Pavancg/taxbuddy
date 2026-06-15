package com.taxbuddy.taxbuddy_ai_chat_service.controller;



import com.taxbuddy.taxbuddy_ai_chat_service.dto.ChatRequest;
import com.taxbuddy.taxbuddy_ai_chat_service.dto.ChatResponse;
import com.taxbuddy.taxbuddy_ai_chat_service.dto.SessionCreateRequest;
import com.taxbuddy.taxbuddy_ai_chat_service.dto.SessionResponse;
import com.taxbuddy.taxbuddy_ai_chat_service.entity.ChatMessage;
import com.taxbuddy.taxbuddy_ai_chat_service.service.ChatService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/chat")

@RequiredArgsConstructor

@Slf4j

public class ChatController {

    private final ChatService chatService;

    @PostMapping("/session")
    public ResponseEntity<SessionResponse> createSession(

            @Valid @RequestBody SessionCreateRequest request) {

        log.info("Create session request for userId: {}",

                request.userId());

        SessionResponse response =

                chatService.createSession(request);

        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(response);

    }

    @PostMapping("/message")

    public ResponseEntity<ChatResponse> chat(

            @Valid @RequestBody ChatRequest request) {

        log.info("Chat message received for session: {}",

                request.sessionId());

        ChatResponse response = chatService.chat(request);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/history/{sessionId}")

    public ResponseEntity<List<ChatMessage>> getChatHistory(

            @PathVariable String sessionId) {

        log.info("Fetching history for session: {}",

                sessionId);

        List<ChatMessage> history =

                chatService.getChatHistory(sessionId);

        return ResponseEntity.ok(history);

    }

    @PutMapping("/session/{sessionId}/close")

    public ResponseEntity<SessionResponse> closeSession(

            @PathVariable String sessionId) {

        log.info("Close session request: {}", sessionId);

        SessionResponse response =

                chatService.closeSession(sessionId);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/health")

    public ResponseEntity<String> health() {

        return ResponseEntity.ok("AI Chat Service is running");

    }

}

