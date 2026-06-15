package com.taxbuddy.taxbuddy_ai_chat_service.config;



import com.taxbuddy.taxbuddy_ai_chat_service.tools.TaxPrefillTools;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;


import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration

public class ChatConfig {

    @Bean

    public ChatClient chatClient(

            ChatClient.Builder builder,

            VectorStore vectorStore) {
        ChatMemory chatMemory =  MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)
                .build();
        return builder

                .defaultSystem("""

                        You are TaxBuddy, a friendly and knowledgeable

                        Indian tax assistant. You help users understand

                        their tax situation, calculate deductions, and

                        file their Income Tax Returns (ITR).

                        You have access to the user's prefilled tax data

                        through tools. Always fetch the user's actual data

                        before answering specific questions about their

                        tax situation.

                        Guidelines:

                        - Always be helpful, clear and concise

                        - Use simple language, avoid complex tax jargon

                        - When showing amounts always use Indian Rupee (₹)

                        - Format large numbers in Indian system

                          (lakhs and crores)

                        - If asked about deductions always check

                          user's actual data first

                        - For general tax questions use your knowledge

                          and the provided context

                        IMPORTANT: If the user expresses frustration,

                        asks to speak with a human, or asks questions

                        you cannot answer confidently, respond with

                        exactly this phrase on a new line:

                        [ESCALATE_REQUESTED]

                        followed by a brief explanation.

                        Example escalation triggers:

                        - "I want to talk to a human"

                        - "Connect me with an expert"

                        - "I need professional advice"

                        - "This is too complicated"

                        - "I don't understand"

                        """)


                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                        //QuestionAnswerAdvisor.builder(vectorStore).build()

                )

                .build();

    }

    @Value("${spring.ai.bedrock.converse.chat.options.model}")
    private String model;

    @PostConstruct
    public void check() {
        System.out.println("MODEL = " + model);
    }

}

