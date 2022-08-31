package com.sros.springbatch.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
@NoArgsConstructor
public class JobRunner extends QuartzJobBean {

    public static final String JOB_TARGET = "target";

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobLocator jobLocator;

    @Override
    protected void executeInternal(final JobExecutionContext executionContext) {
        /*final var jobDataMap = executionContext.getMergedJobDataMap();
        final var jobName = jobDataMap.getString(JOB_TARGET);
        log.info("Schedule job target: {} triggered", jobName);
        try {
            final var job = jobLocator.getJob(jobName);
            final var params = new JobParametersBuilder()
                    .addString("JobId", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            jobLauncher.run(job, params);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
