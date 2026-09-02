package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Agents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class HrAgent {

    private static final Logger log = LoggerFactory.getLogger(HrAgent.class);

    private final ChatClient chatClient;
    private final ToolCallbackProvider mcpToolCallbackProvider;

    public HrAgent(ChatClient.Builder builder,
                   VectorStore hrVectorStore,
                   ChatMemory chatMemory,
                   ToolCallbackProvider mcpToolCallbackProvider) {
        this.mcpToolCallbackProvider = mcpToolCallbackProvider;

        ToolCallback[] startupTools = mcpToolCallbackProvider.getToolCallbacks();
        log.info("HR agent initialized with {} MCP tool callback(s)", startupTools.length);

        this.chatClient = builder
                .defaultSystem("""
                    You are an HR Assistant.
                    Answer only HR related questions.
                    Use only available MCP tools by exact name.
                    Never invent tool names.
                    The email tool is sendEmail (camelCase). Never use send_email.
                    For email/send requests, call the MCP email tool.
                    Never claim an email was sent unless the MCP tool response explicitly confirms success.
                    If the tool is unavailable or fails, clearly state that email was not sent.
                    """)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor
                                .builder(chatMemory)
                                .build(),
                        QuestionAnswerAdvisor.builder(hrVectorStore)
                                .build())
                .build();
    }

    public String answer(String question,String conversationId) {
        ToolCallback[] mcpTools = mcpToolCallbackProvider.getToolCallbacks();

        if (mcpTools.length == 0 && looksLikeEmailRequest(question)) {
            return "I cannot send this email because the MCP email tool is currently unavailable.";
        }

        ChatClientRequestSpec requestSpec = chatClient.prompt()
                .user(question)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId));

        if (mcpTools.length > 0) {
            requestSpec = requestSpec.tools((Object[]) mcpTools);
        }

        try {
            return requestSpec.call().content();
        }
        catch (RuntimeException ex) {
            if (isMcpEmailTimeout(ex)) {
                log.warn("MCP sendEmail timed out", ex);
                return "I could not send the email because the email service connection timed out. "
                        + "Please retry in a moment or contact IT to check SMTP/network connectivity.";
            }

            if (isMcpToolFailure(ex)) {
                log.warn("MCP email tool failed", ex);
                return "I could not send the email because the MCP email tool returned an error. "
                        + "Please verify email service configuration and try again.";
            }

            throw ex;
        }
    }

    private boolean looksLikeEmailRequest(String question) {
        if (question == null) {
            return false;
        }

        String q = question.toLowerCase();
        return q.contains("email") || q.contains("mail") || q.contains("send");
    }

    private boolean isMcpEmailTimeout(Throwable throwable) {
        for (Throwable current = throwable; current != null; current = current.getCause()) {
            String message = current.getMessage();
            if (message == null) {
                continue;
            }

            String normalized = message.toLowerCase();
            if (normalized.contains("sendemail") && normalized.contains("timed out")) {
                return true;
            }
            if (normalized.contains("connection timed out")) {
                return true;
            }
        }

        return false;
    }

    private boolean isMcpToolFailure(Throwable throwable) {
        for (Throwable current = throwable; current != null; current = current.getCause()) {
            String message = current.getMessage();
            if (message == null) {
                continue;
            }

            String normalized = message.toLowerCase();
            if (normalized.contains("error calling tool") || normalized.contains("sendemail")) {
                return true;
            }
        }

        return false;
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