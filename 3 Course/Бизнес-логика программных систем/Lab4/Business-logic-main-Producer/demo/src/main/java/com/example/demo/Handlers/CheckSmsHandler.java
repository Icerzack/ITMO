package com.example.demo.Handlers;

import com.example.demo.Dto.Responses.CheckSmsResponse;
import com.example.demo.Dto.Responses.CheckSumResponse;
import com.example.demo.Service.OrderService;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@ExternalTaskSubscription("checkSms")
public class CheckSmsHandler implements ExternalTaskHandler {
    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        long id = Long.parseLong(externalTask.getVariable("user_id"));
        String phone = externalTask.getVariable("phone");
        String sms = externalTask.getVariable("sms");

        VariableMap variables = Variables.createVariables();

        CheckSmsResponse checkSmsResponse = orderService.checkSms(id, phone, sms);
//        variables.put("user_id", id);
        variables.put("check_sms", checkSmsResponse.isResult());

        externalTaskService.complete(externalTask, variables);

        Logger.getLogger("checkSum")
                .log(Level.INFO, "Sms {0} for userID {1} is checked!", new Object[]{sms, id});
    }
}
