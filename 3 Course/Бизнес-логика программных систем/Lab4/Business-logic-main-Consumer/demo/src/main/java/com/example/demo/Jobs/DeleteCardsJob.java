package com.example.demo.Jobs;

import com.example.demo.Dao.Payment.PaymentsRepository;
import com.example.demo.Service.QuartzService;
import com.example.demo.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class DeleteCardsJob implements Job {
    @Autowired
    private QuartzService quartzService;

    @Override
    public void execute(JobExecutionContext context) {
//        System.out.println("Running cron task on deleting cards");
//        quartzService.deleteNonValidCards();
    }

}
