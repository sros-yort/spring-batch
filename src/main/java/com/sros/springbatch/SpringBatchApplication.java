package com.sros.springbatch;

import com.github.javafaker.Faker;
import com.sros.springbatch.model.Book;
import com.sros.springbatch.model.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Slf4j
@EnableBatchProcessing
@SpringBootApplication
@RequiredArgsConstructor
public class SpringBatchApplication {

    private final Faker faker = Faker.instance();
    private final JobExplorer jobs;

    private final BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

    @PostConstruct
    public void init() {
        jobs.getJobNames().forEach(name -> log.info("job name: {}", name));


        // init book test
        for (int i = 0; i < 1000; i++) {
            bookRepository.save(new Book(faker.book().title(), faker.book().author(), faker.book().genre()));
        }
    }

}
