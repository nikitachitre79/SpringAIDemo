package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.fdmgroup.Spring_AI_Demo.service.LeaveService;

@Component
public class HrTools {

    private final LeaveService leaveService;

    public HrTools(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @Tool(description = "Get employee leave balance")
    public String getLeaveBalance(String employeeId) {
        if(employeeId.isEmpty())
        {
            employeeId = "e123";
        }
        return "Balance: " +
                leaveService.getBalance(employeeId);
    }
}