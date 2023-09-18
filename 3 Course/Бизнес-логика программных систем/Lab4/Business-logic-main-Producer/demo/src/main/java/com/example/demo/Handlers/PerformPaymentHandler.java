package com.example.demo.Handlers;

import com.example.demo.Dto.Requests.PerformPaymentRequest;
import com.example.demo.Dto.Responses.CheckSmsResponse;
import com.example.demo.Dto.Responses.PerformPaymentResponse;
import com.example.demo.Service.OrderService;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@ExternalTaskSubscription("performPayment")
public class PerformPaymentHandler implements ExternalTaskHandler {
    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        long id = Long.parseLong(externalTask.getVariable("user_id"));
        String cardNum = externalTask.getVariable("card_number");
        String cardDate = externalTask.getVariable("card_date");
        String cardCVV = externalTask.getVariable("card_cvv");
        double cost = externalTask.getVariable("cost");
        String address = externalTask.getVariable("address");

        VariableMap variables = Variables.createVariables();

        PerformPaymentResponse performPaymentResponse = orderService.performPayment(id, cardNum, cardDate, cardCVV, cost, address);
//        variables.put("user_id", id);
        variables.put("perform_payment", performPaymentResponse.isResult());

        externalTaskService.complete(externalTask, variables);

        Logger.getLogger("performPayment")
                .log(Level.INFO, "Payment for userID {0} is done!", new Object[]{id});
    }
}
