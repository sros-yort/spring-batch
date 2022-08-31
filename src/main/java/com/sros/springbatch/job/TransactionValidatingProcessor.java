package com.sros.springbatch.job;

import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

public class TransactionValidatingProcessor extends ValidatingItemProcessor<String> {
    public TransactionValidatingProcessor() {
        super(item -> {
            if (item.length() > 255) {
                throw new ValidationException("String must be less than 255");
            }
        });
        setFilter(true);
    }
}
