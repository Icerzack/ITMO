package com.example.demo.Handlers;

import com.example.demo.Service.QuartzService;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ExternalTaskSubscription("deleteCards") // create a subscription for this topic name
public class DeleteCardsHandler implements ExternalTaskHandler {

    @Autowired
    QuartzService quartzService;
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        quartzService.deleteNonValidCards();
        externalTaskService.complete(externalTask);
    }
}
