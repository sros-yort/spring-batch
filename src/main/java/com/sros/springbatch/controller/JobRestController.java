package com.sros.springbatch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/jobs")
public class JobRestController {

    private final JobLauncher jobLauncher;
    private final JobLocator jobLocator;

    @PostMapping
    public ResponseEntity trigger() {
        try {
            final var jobParameters = new JobParametersBuilder()
                    .addString("UUID", UUID.randomUUID().toString())
                    .toJobParameters();
            jobLauncher.run(jobLocator.getJob("bookJob"), jobParameters);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResponseEntity.ok().build();
    }

}
