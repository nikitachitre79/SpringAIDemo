package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class PolicyTools {

    @Tool(description = "Get latest company policy version")
    public String latestPolicyVersion() {

        return "Remote Work Policy v3.2";
    }
}