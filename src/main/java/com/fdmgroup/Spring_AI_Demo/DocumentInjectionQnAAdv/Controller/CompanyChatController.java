package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Agents.CompanyCopilot;
import com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.DTO.ChatRequest;

@RestController
@RequestMapping("/api/chat")
public class CompanyChatController {

 

	    private final CompanyCopilot companyCopilot;

	    public CompanyChatController(
	            CompanyCopilot companyCopilot) {

	        this.companyCopilot = companyCopilot;
	    }

	    @PostMapping
	    public String ask(
	            @RequestBody ChatRequest request) {

	        return companyCopilot.ask(
	                request.conversationId(),
	                request.question());
	    }
	}

