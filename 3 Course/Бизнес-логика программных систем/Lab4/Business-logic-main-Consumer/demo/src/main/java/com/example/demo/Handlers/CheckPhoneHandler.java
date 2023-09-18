package com.example.demo.Handlers;

import com.example.demo.Dto.Responses.CheckPhoneResponse;
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
@ExternalTaskSubscription("checkPhone") // create a subscription for this topic name
public class CheckPhoneHandler implements ExternalTaskHandler {
  @Autowired
  private UserService userService;

  @Override
  public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
    long id = Long.parseLong(externalTask.getVariable("user_id"));

    CheckPhoneResponse userPhone = userService.checkPhone(id);

    VariableMap variables = Variables.createVariables();
//    variables.put("user_id", id);
    variables.put("check_phone", userPhone.isResult());

    if (userPhone.isResult()) {
      variables.put("phone", userPhone.getPhone());
    }

    externalTaskService.complete(externalTask, variables);

    Logger.getLogger("checkPhone")
        .log(Level.INFO, "Phone {0} for userID {1} is checked!", new Object[]{userPhone.getPhone(), id});
  }

}