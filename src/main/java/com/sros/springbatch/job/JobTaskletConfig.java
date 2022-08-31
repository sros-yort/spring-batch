package com.sros.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class JobTaskletConfig {

    private final JobBuilderFactory jobBuilders;
    private final StepBuilderFactory stepBuilders;
    private final JobLauncher jobLauncher;

//    @Scheduled(fixedRate = 5000)
    public void run() throws Exception {
        final var execution = jobLauncher.run(jobWithTasklet(), new JobParametersBuilder().toJobParameters());
        log.info("Execution status: {}", execution.getStatus());
    }

    @Bean
    public Job jobWithTasklet() {
        return jobBuilders.get("jobWithTasklet")
                .start(taskletStep())
                .build();
    }

    @Bean
    public Step taskletStep() {
        return stepBuilders.get("taskletStep")
                .tasklet(tasklet())
                .build();
    }

    @Bean
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            for (int i = 0; i < 10; i++) {
                log.info("Executing tasklet step {} times", i);
            }
            return RepeatStatus.FINISHED;
        };
    }

}
