package com.sros.springbatch.job;

import com.sros.springbatch.job.JobRunner;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

import static com.sros.springbatch.job.JobRunner.JOB_TARGET;

@Configuration
public class QuartzConfiguration {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        final var scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(jobWithChunkTrigger());
        scheduler.setJobDetails(jobWithChunkDetail());
        scheduler.setQuartzProperties(quartzProperties());
        return scheduler;
    }

    @Bean
    public Trigger jobWithChunkTrigger() {
        final var scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(10)
                .repeatForever();

        return TriggerBuilder
                .newTrigger()
                .forJob(jobWithChunkDetail())
                .withIdentity("jobWithChunkTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail jobWithChunkDetail() {
        final var jobDataMap = new JobDataMap();
        jobDataMap.put(JOB_TARGET, "jobWithChunk");
        return JobBuilder.newJob(JobRunner.class)
                .withIdentity("jobWithChunk")
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Bean
    public Properties quartzProperties() {
        final var properties = new Properties();
        properties.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
        properties.put("org.quartz.threadPool.threadCount", "3");
        properties.put("org.quartz.scheduler.instanceName", "LongChong");
        return properties;
    }

}
