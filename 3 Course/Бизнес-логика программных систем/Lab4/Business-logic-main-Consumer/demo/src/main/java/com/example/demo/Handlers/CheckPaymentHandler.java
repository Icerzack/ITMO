package com.example.demo.Handlers;

import com.example.demo.Dto.Responses.CheckPaymentResponse;
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
@ExternalTaskSubscription("checkPayment") // create a subscription for this topic name
public class CheckPaymentHandler implements ExternalTaskHandler {
    @Autowired
    private UserService userService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        long id = Long.parseLong(externalTask.getVariable("user_id"));

        CheckPaymentResponse userPayment = userService.checkPayment(id);

        VariableMap variables = Variables.createVariables();
//        variables.put("user_id", id);
        variables.put("check_payment", userPayment.isResult());

        if (userPayment.isResult()) {
            variables.put("card_number", userPayment.getCardNumber());
            variables.put("card_date", userPayment.getCardDate());
            variables.put("card_cvv", userPayment.getCardCVV());
        }

        externalTaskService.complete(externalTask, variables);

        Logger.getLogger("checkPayment")
                .log(Level.INFO, "Payment {0} for userID {1} provided!", new Object[]{userPayment.getCardNumber(), id});
    }
}