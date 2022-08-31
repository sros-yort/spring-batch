package com.sros.springbatch.job.book;

import com.github.javafaker.Faker;
import com.sros.springbatch.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class BookItemProcessor implements ItemProcessor<Book, Book> {

    private static final Faker FAKER = Faker.instance();

    @Override
    public Book process(final Book item) {
        log.info("{} : {}", item.getId(), item.getAuthor());
        item.setPublisher(FAKER.book().publisher());
        return item;
    }

}
