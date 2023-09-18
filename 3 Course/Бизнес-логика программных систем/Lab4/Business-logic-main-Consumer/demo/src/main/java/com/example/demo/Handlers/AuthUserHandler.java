package com.example.demo.Handlers;

import com.example.demo.Dto.Responses.AddPhoneResponse;
import com.example.demo.Service.UserService;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@ExternalTaskSubscription("authUser")

public class AuthUserHandler implements ExternalTaskHandler {

    @Autowired
    private UserService userService;
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String username = externalTask.getVariable("username");
        String password = externalTask.getVariable("password");

        VariableMap variables = Variables.createVariables();

        if (userService.checkPassword(Long.parseLong(username), password)) {
            if (userService.isIdAdmin(Long.parseLong(username))) {
                variables.put("is_admin", true);
            } else {
                variables.put("is_admin", false);
            }
            variables.put("user_id", username);
            variables.put("auth_result", true);
        } else {
            variables.put("auth_result", false);
        }
        externalTaskService.complete(externalTask, variables);

    }
}
