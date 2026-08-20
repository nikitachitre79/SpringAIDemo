package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Service.RagServiceQnA;
//User Question
//↓
//Create Embedding
//↓
//Vector Search
//↓
//Find Top Matching Chunks
//↓
//Inject Chunks into Prompt
//↓
//Gemini
//↓
//Final Answer

@RestController
@RequestMapping("/api/ragQnA")
public class RagControllerQnA {

	    private final RagServiceQnA ragServiceqna;

	    public RagControllerQnA(RagServiceQnA ragServiceqna) {
	        this.ragServiceqna = ragServiceqna;
	    }

	    @GetMapping
	    public String ask(
	            @RequestParam String question) {

	        return ragServiceqna.ask(question);
	    }
	}