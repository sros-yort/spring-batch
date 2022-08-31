package com.sros.springbatch.job.book;

import com.sros.springbatch.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.Date;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BookJobConfig {

    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final JobRepository jobRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final JobLauncher jobLauncher;

    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void run() throws Exception {
        final var jobParameters = new JobParametersBuilder()
                .addDate("scheduling.date", new Date()).toJobParameters();
        final var execution = jobLauncher.run(job(), jobParameters);
        log.info("Execution status: {}", execution.getStatus());
    }

    @Bean(name = "bookJob")
    public Job job() {
        return jobBuilderFactory.get("bookJob")
                .repository(jobRepository)
                .start(step())
                .build();
    }

    @Bean(name = "bookStep")
    public Step step() {
        return stepBuilderFactory.get("bookStep")
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .<Book, Book>chunk(100)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private JpaPagingItemReader<Book> itemReader() {
        return new JpaPagingItemReaderBuilder<Book>()
                .entityManagerFactory(entityManagerFactory)
                .name("Book")
                .queryString("from books")
                .build();
    }

    private ItemProcessor<Book, Book> itemProcessor() {
        return new BookItemProcessor();
    }

    private JpaItemWriter<Book> itemWriter() {
        return new JpaItemWriterBuilder<Book>().entityManagerFactory(entityManagerFactory).build();
    }

}
