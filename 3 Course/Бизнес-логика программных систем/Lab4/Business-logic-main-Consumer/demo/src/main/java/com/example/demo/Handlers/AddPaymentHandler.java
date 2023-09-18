package com.example.demo.Handlers;

import com.example.demo.Dto.Responses.AddPaymentResponse;
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

import java.net.CacheRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@ExternalTaskSubscription("addPayment") // create a subscription for this topic name
public class AddPaymentHandler implements ExternalTaskHandler {
    @Autowired
    private UserService userService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        long id = Long.parseLong(externalTask.getVariable("user_id"));
        String cardNum = externalTask.getVariable("card_number");
        String cardDate = externalTask.getVariable("card_date");
        String cardCVV = externalTask.getVariable("card_cvv");

        AddPaymentResponse addPaymentResponse = userService.addPayment(id, cardNum, cardDate, cardCVV);

        VariableMap variables = Variables.createVariables();
        variables.put("user_id", id);
        variables.put("add_payment_result", addPaymentResponse.isResult());
        variables.put("card_number", cardNum);
        variables.put("card_date", cardDate);
        variables.put("card_cvv", cardCVV);

        externalTaskService.complete(externalTask, variables);

        Logger.getLogger("addPayment")
                .log(Level.INFO, "Payment {0} for userID {1} added!", new Object[]{cardNum, id});
    }

}