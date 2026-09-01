package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Tools.HrTools;

@Service
public class HrAgent {

    private final ChatClient chatClient;

    public HrAgent(ChatClient.Builder builder,
                   VectorStore hrVectorStore,
                   ChatMemory chatMemory,
                   HrTools hrTools) {

        this.chatClient = builder
                .defaultSystem("""
                    You are an HR Assistant.
                    Answer only HR related questions.
                    For leave balance questions, use the getLeaveBalance tool.
                    If employeeId is missing, ask the user to provide employeeId.
                    """)
                .defaultTools(hrTools)
                .defaultAdvisors(
                		MessageChatMemoryAdvisor
                		.builder(chatMemory)
                		.build(),
                        QuestionAnswerAdvisor.builder(hrVectorStore)
                                .build())
                .build();
    }

    public String answer(String question,String conversationId) {

        return chatClient.prompt()
                .user(question)
                .advisors(a -> a.param(
                		ChatMemory.CONVERSATION_ID,
                		conversationId))
                .call()
                .content();
    }
}
/* for general RAG
{
  "conversationId": "user123",
  "question": "give me information about persormance reviews"
}
  */

/* for using tools
{
  "conversationId": "user123",
  "question": "what is my leave balance my employeeId is e123"
}
*/