package com.example.demo.Handlers;

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
@ExternalTaskSubscription("checkSum")
public class CheckSumHandler implements ExternalTaskHandler {
    @Autowired
    private OrderService orderService;
    Map<String, Float> products = new HashMap<>();
    final String pizzaName = "pizza";
    final String juiceName = "juice";
    final String sushiName = "sushi";

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        products.put(pizzaName, 359.0f);
        products.put(juiceName, 70.0f);
        products.put(sushiName, 900.0f);

        long id = Long.parseLong(externalTask.getVariable("user_id"));
        boolean pizzaExist = externalTask.getVariable(pizzaName);
        boolean juiceExist = externalTask.getVariable(juiceName);
        boolean sushiExist = externalTask.getVariable(sushiName);

        double sum = 0;
        if (pizzaExist) {
            sum += products.get(pizzaName);
        }
        if (juiceExist) {
            sum += products.get(juiceName);
        }
        if (sushiExist) {
            sum += products.get(sushiName);
        }

        VariableMap variables = Variables.createVariables();

        CheckSumResponse checkSumResponse = orderService.checkSum(id, sum);
//        variables.put("user_id", id);
        variables.put("order_id", 666);
        variables.put("check_sum", checkSumResponse.isResult());
        variables.put("cost", sum);

        externalTaskService.complete(externalTask, variables);

        Logger.getLogger("checkSum")
                .log(Level.INFO, "Sum {0} for userID {1} is checked!", new Object[]{sum, id});
    }
}
