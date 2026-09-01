package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Agents;

import org.springframework.stereotype.Service;
import com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Agents.AgentType;
@Service
public class CompanyCopilot {

    private final RouterAgent router;

    private final HrAgent hrAgent;
    private final ItAgent itAgent;
    private final PolicyAgent policyAgent;

    public CompanyCopilot(
            RouterAgent router,
            HrAgent hrAgent,
            ItAgent itAgent,
            PolicyAgent policyAgent) {

        this.router = router;
        this.hrAgent = hrAgent;
        this.itAgent = itAgent;
        this.policyAgent = policyAgent;
    }

    public String ask(String conversationId,String question) {

        AgentType agentType =
                router.route(question);

        if (agentType == AgentType.HR) {
            return hrAgent.answer(question,
            		conversationId);
        }
        if (agentType == AgentType.IT) {
            return itAgent.answer(question,
            		conversationId);
        }
        if (agentType == AgentType.POLICY) {
            return policyAgent.answer(question,
            		conversationId);
        }

        throw new IllegalStateException("Unexpected agent type: " + agentType);
    }
}