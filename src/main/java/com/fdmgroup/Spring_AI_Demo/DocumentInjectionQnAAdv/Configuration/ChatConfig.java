package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder builder,
            VectorStore vectorStore,
            ChatMemory chatMemory) {

    return builder
            .defaultAdvisors(

                    MessageChatMemoryAdvisor.builder(chatMemory)
                            .build(),

                    QuestionAnswerAdvisor.builder(vectorStore)
                            .searchRequest(
                                    SearchRequest.builder()
                                            .topK(5)
                                            .similarityThreshold(0.7)
                                            .build())
                            .build()

            )
            .build();
        }
}