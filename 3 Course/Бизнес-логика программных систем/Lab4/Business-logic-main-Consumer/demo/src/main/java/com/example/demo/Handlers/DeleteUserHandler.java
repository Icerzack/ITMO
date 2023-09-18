package com.example.demo.Handlers;

import com.example.demo.Service.UserService;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ExternalTaskSubscription("deletePhone")
public class DeleteUserHandler implements ExternalTaskHandler {
    @Autowired
    private UserService userService;
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String id = externalTask.getVariable("id");

        userService.deleteUser(Long.parseLong(id));
        externalTaskService.complete(externalTask);

    }
}
