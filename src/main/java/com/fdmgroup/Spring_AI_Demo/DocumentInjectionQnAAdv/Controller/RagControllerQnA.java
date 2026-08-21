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
			@RequestParam String conversationId,
	            @RequestParam String question) {

	        return ragServiceqna.ask(conversationId,question);
	    }

		//Questions after adding memory My name is Nikita
		//what is my name
		//add conversationId e.g. 123
		// we can add DTOs for request/response
		/*
		public record ChatRequest(
        String conversationId,
        String question) {
}
		public record ChatResponse(
        String answer) {
}

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final RagService ragService;

    public ChatController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping
    public ChatResponse chat(
            @RequestBody ChatRequest request) {

        String response = ragService.ask(
                request.conversationId(),
                request.question());

        return new ChatResponse(response);
    }
}

{
  "conversationId": "user-123",
  "question": "What is Spring AI?"
}

If you're building a web application, generate a UUID when the chat starts:

String conversationId =
        UUID.randomUUID().toString();

	Store it on the frontend and send it with every request.
	
	{
  "conversationId": "550e8400-e29b-41d4-a716-446655440000",
  "question": "Tell me about company benefits"
}
	This keeps:
	User A → Memory A
	User B → Memory B
	User C → Memory C

separate while all users share the same RAG knowledge base in your VectorStore.

Question
   ↓
Memory Lookup (conversationId)
   ↓
Vector Search (RAG)
   ↓
Document Injection
   ↓
Gemini
   ↓
Answer

So your controller only needs to pass the conversationId; Spring AI handles both memory retrieval and knowledge retrieval automatically.
Without conversationId
Every request looks independent.
Request 1:
My name is Nikita

Request 2:
What is my name?

The model may not remember because there's no conversation history associated with the request.
*/
	}