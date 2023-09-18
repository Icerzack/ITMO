package com.example.demo.Jobs;

import com.example.demo.Service.QuartzService;
import com.example.demo.Service.UserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.quartz.Job;

@Component
public class DeleteUsersJob implements Job {

    @Autowired
    private QuartzService quartzService;

    @Override
    public void execute(JobExecutionContext context) {
//        System.out.println("Running cron task on deleting accounts");
//        quartzService.deleteUnusedAccounts();
    }

}
