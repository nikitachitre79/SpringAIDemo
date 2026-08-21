package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class RagServiceQnA {

    private final ChatClient chatClient;

    public RagServiceQnA(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String ask(String conversationId,String question) {

        return chatClient.prompt()
                .user(question)
                .advisors(advisor -> advisor.param(
                ChatMemory.CONVERSATION_ID,
                    conversationId))
                .call()
                .content();
    }
}
