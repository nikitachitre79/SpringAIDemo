package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class RagServiceQnA {

    private final ChatClient chatClient;

    public RagServiceQnA(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String ask(String question) {

        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
