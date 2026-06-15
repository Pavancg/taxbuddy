package com.taxbuddy.taxbuddy_ai_chat_service.service;


import com.taxbuddy.taxbuddy_ai_chat_service.dto.ChatRequest;
import com.taxbuddy.taxbuddy_ai_chat_service.dto.ChatResponse;
import com.taxbuddy.taxbuddy_ai_chat_service.dto.SessionCreateRequest;
import com.taxbuddy.taxbuddy_ai_chat_service.dto.SessionResponse;
import com.taxbuddy.taxbuddy_ai_chat_service.entity.ChatMessage;
import com.taxbuddy.taxbuddy_ai_chat_service.entity.ChatSession;
import com.taxbuddy.taxbuddy_ai_chat_service.entity.MessageRole;
import com.taxbuddy.taxbuddy_ai_chat_service.entity.SessionStatus;
import com.taxbuddy.taxbuddy_ai_chat_service.repository.ChatMessageRepository;
import com.taxbuddy.taxbuddy_ai_chat_service.repository.ChatSessionRepository;
import com.taxbuddy.taxbuddy_ai_chat_service.tools.TaxPrefillTools;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;

import java.util.UUID;


@Service

@RequiredArgsConstructor

@Slf4j

public class ChatService {

    private final ChatClient chatClient;

    private final ChatSessionRepository chatSessionRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final TaxPrefillTools taxPrefillTools;

    private static final String ESCALATION_SENTINEL =

            "[ESCALATE_REQUESTED]";

    @Transactional

    public SessionResponse createSession(

            SessionCreateRequest request) {

        log.info("Creating new chat session for userId: {}",

                request.userId());

        String sessionId = UUID.randomUUID().toString();

        ChatSession session = ChatSession.builder()

                .sessionId(sessionId)

                .userId(request.userId())

                .status(SessionStatus.ACTIVE)

                .build();

        chatSessionRepository.save(session);

        log.info("Chat session created: {}", sessionId);

        return SessionResponse.created(sessionId,

                request.userId());

    }

    @Transactional

    public ChatResponse chat(ChatRequest request) {

        log.info("Processing chat request for session: {}",

                request.sessionId());

        ChatSession session = chatSessionRepository

                .findBySessionId(request.sessionId())

                .orElseThrow(() -> new RuntimeException(

                        "Session not found: "

                                + request.sessionId()));

        if (session.getStatus() == SessionStatus.ESCALATED) {

            return ChatResponse.ai(

                    request.sessionId(),

                    "This session has been escalated to a " +

                            "human expert. Please wait for them " +

                            "to connect with you.");

        }

        if (session.getStatus() == SessionStatus.CLOSED) {

            return ChatResponse.ai(

                    request.sessionId(),

                    "This session is closed. " +

                            "Please create a new session.");

        }

        saveMessage(session, MessageRole.USER,

                request.message(), false);

        String userMessageWithContext = buildContextualMessage(

                request.message(), session.getUserId());

        String aiResponse = chatClient.prompt()

                .user(userMessageWithContext)
                .tools(taxPrefillTools)
                .advisors(advisorSpec -> advisorSpec

                        .param(ChatMemory.CONVERSATION_ID,

                                request.sessionId()))

                .call()

                .content();

        log.info("AI response received for session: {}",

                request.sessionId());

        if (aiResponse != null &&

                aiResponse.contains(ESCALATION_SENTINEL)) {

            return handleEscalation(session, aiResponse);

        }

        saveMessage(session, MessageRole.ASSISTANT,

                aiResponse, false);

        return ChatResponse.ai(request.sessionId(), aiResponse);

    }

    @Transactional

    public List<ChatMessage> getChatHistory(String sessionId) {

        log.info("Fetching chat history for session: {}",

                sessionId);

        ChatSession session = chatSessionRepository

                .findBySessionId(sessionId)

                .orElseThrow(() -> new RuntimeException(

                        "Session not found: " + sessionId));

        return chatMessageRepository

                .findAllByChatSessionOrderByCreatedAtAsc(session);

    }

    @Transactional

    public SessionResponse closeSession(String sessionId) {

        log.info("Closing session: {}", sessionId);

        ChatSession session = chatSessionRepository

                .findBySessionId(sessionId)

                .orElseThrow(() -> new RuntimeException(

                        "Session not found: " + sessionId));

        session.setStatus(SessionStatus.CLOSED);

        chatSessionRepository.save(session);

        return new SessionResponse(

                sessionId,

                session.getUserId(),

                SessionStatus.CLOSED,

                session.getCreatedAt(),

                "Session closed successfully"

        );

    }

    private ChatResponse handleEscalation(

            ChatSession session, String aiResponse) {

        log.info("Escalation requested for session: {}",

                session.getSessionId());

        String cleanResponse = aiResponse

                .replace(ESCALATION_SENTINEL, "")

                .trim();

        String escalationReason = extractEscalationReason(

                aiResponse);

        session.setStatus(SessionStatus.ESCALATED);

        session.setEscalatedAt(LocalDateTime.now());

        session.setEscalationReason(escalationReason);

        chatSessionRepository.save(session);

        saveMessage(session, MessageRole.ASSISTANT,

                aiResponse, true);

        log.info("Session {} escalated. Reason: {}",

                session.getSessionId(), escalationReason);

        return ChatResponse.escalation(

                session.getSessionId(),

                cleanResponse + "\n\nI am connecting you " +

                        "with a tax expert who will assist you " +

                        "shortly. Please hold on.",

                escalationReason

        );

    }

    private String buildContextualMessage(

            String message, Long userId) {

        return String.format("""

                User ID: %d

                User Question: %s

                Note: When fetching tax data, use userId: %d

                """, userId, message, userId);

    }

    private String extractEscalationReason(String aiResponse) {

        if (aiResponse.contains("I want to talk to a human")) {

            return "User requested human expert";

        } else if (aiResponse.contains("too complicated")) {

            return "Query too complex for AI";

        } else if (aiResponse.contains("professional advice")) {

            return "User needs professional advice";

        }

        return "User requested expert assistance";

    }

    private void saveMessage(ChatSession session,

                             MessageRole role,

                             String content,

                             boolean escalationTrigger) {

        ChatMessage message = ChatMessage.builder()

                .chatSession(session)

                .role(role)

                .content(content)

                .escalationTrigger(escalationTrigger)

                .build();

        chatMessageRepository.save(message);

    }

}

