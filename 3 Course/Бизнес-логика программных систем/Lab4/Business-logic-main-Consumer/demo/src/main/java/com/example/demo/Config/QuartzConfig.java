package com.example.demo.Config;

import com.example.demo.Jobs.DeleteCardsJob;
import com.example.demo.Jobs.DeleteUsersJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzConfig {
    //https://www.freeformatter.com/cron-expression-generator-quartz.html
    @Bean
    public JobDetailFactoryBean deleteCardsJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DeleteCardsJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean deleteUsersJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DeleteUsersJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean deleteCardsTrigger(JobDetail deleteCardsJobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(deleteCardsJobDetail);
        factoryBean.setStartDelay(0);
        factoryBean.setCronExpression("0/5 * * ? * * *");
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean deleteUsersTrigger(JobDetail deleteUsersJobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(deleteUsersJobDetail);
        factoryBean.setStartDelay(0);
        factoryBean.setCronExpression("0/5 * * ? * * *");
        return factoryBean;
    }

}
