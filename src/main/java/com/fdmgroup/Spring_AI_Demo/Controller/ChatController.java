package com.fdmgroup.Spring_AI_Demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.Spring_AI_Demo.service.ChatService;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

    @GetMapping("/ask")
    public String ask(@RequestParam String message) {
        return chatService.ask(message);
    }
}