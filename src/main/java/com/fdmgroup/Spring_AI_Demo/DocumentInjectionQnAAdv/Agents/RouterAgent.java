package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Agents;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class RouterAgent {

    private static final Pattern AGENT_TOKEN_PATTERN =
            Pattern.compile("\\b(HR|IT|POLICY)\\b", Pattern.CASE_INSENSITIVE);

    private final ChatClient chatClient;

    public RouterAgent(ChatClient.Builder builder) {

        this.chatClient = builder.build();
    }

    public AgentType route(String question) {

        AgentType keywordMatch = routeByKeyword(question);

        if (keywordMatch != null) {
            return keywordMatch;
        }

        String response =
                chatClient.prompt()
                        .user("""
                            Classify the question into one category: HR, IT, or POLICY.
                            Reply with exactly one token: HR or IT or POLICY.
                            Do not add any explanation, punctuation, or extra words.

                            Question:
                            %s
                            """.formatted(question))
                        .call()
                        .content();

        AgentType parsedType = parseAgentType(response);

        if (parsedType != null) {
            return parsedType;
        }

        throw new IllegalStateException(
                "Unable to classify agent type from response: " + response);
    }

    private AgentType parseAgentType(String response) {

        if (response == null) {
            return null;
        }

        Matcher matcher =
                AGENT_TOKEN_PATTERN.matcher(response);

        if (matcher.find()) {
            return AgentType.valueOf(
                    matcher.group(1)
                            .toUpperCase(Locale.ROOT));
        }

        return null;
    }

    private AgentType routeByKeyword(String question) {

        if (question == null || question.isBlank()) {
            return null;
        }

        String q = question.toLowerCase(Locale.ROOT);

        if (q.contains("leave") ||
                q.contains("vacation") ||
                q.contains("sick") ||
                q.contains("benefit") ||
                q.contains("payroll") ||
                q.contains("hr")) {
            return AgentType.HR;
        }

        if (q.contains("password") ||
                q.contains("laptop") ||
                q.contains("vpn") ||
                q.contains("email") ||
                q.contains("network") ||
                q.contains("it")) {
            return AgentType.IT;
        }

        if (q.contains("policy") ||
                q.contains("compliance") ||
                q.contains("conduct") ||
                q.contains("security")) {
            return AgentType.POLICY;
        }

        return null;
    }
}
