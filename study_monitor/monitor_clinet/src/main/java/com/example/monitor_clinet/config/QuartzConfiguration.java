package com.example.monitor_clinet.config;

import com.example.monitor_clinet.task.MonitorRuntimeTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {
    @Bean
    JobDetail monitorJob() {
        return JobBuilder.newJob(MonitorRuntimeTask.class)
                .withIdentity("monitorJob")
                .storeDurably()
                .build();
    }
    @Bean
    Trigger monitorTrigger(JobDetail detail) {
        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(detail)
                .withIdentity("monitor-trigger")
                .withSchedule(cron)
                .build();
    }
}
    // ... other Quartz configurations
