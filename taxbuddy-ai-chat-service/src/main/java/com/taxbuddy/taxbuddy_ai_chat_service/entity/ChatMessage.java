package com.taxbuddy.taxbuddy_ai_chat_service.entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity

@Table(name = "chat_messages")

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

public class ChatMessage {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "session_id", nullable = false)

    private ChatSession chatSession;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false)

    private MessageRole role;

    @Column(nullable = false, columnDefinition = "TEXT")

    private String content;

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

    @Column(nullable = false)

    private boolean escalationTrigger;

    @PrePersist

    protected void onCreate() {

        createdAt = LocalDateTime.now();

    }

}
