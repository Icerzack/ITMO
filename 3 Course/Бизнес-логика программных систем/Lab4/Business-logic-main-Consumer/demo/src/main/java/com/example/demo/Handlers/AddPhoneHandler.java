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
@ExternalTaskSubscription("addPhone") // create a subscription for this topic name
public class AddPhoneHandler implements ExternalTaskHandler {
    @Autowired
    private UserService userService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        long id = Long.parseLong(externalTask.getVariable("user_id"));
        String phone = externalTask.getVariable("phone");

        AddPhoneResponse addPhoneResponse = userService.addPhone(id, phone);

        VariableMap variables = Variables.createVariables();
//        variables.put("user_id", id);
        variables.put("add_phone_result", addPhoneResponse.isResult());
        variables.put("phone", phone);

        externalTaskService.complete(externalTask, variables);

        Logger.getLogger("addPhone")
                .log(Level.INFO, "Phone {0} for userID {1} added!", new Object[]{phone, id});
    }

}