package com.flowforge.chain;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

@Component
public class DueDateValidationHandler
        extends TaskValidationHandler {

    @Override
    protected void validate(
            TaskValidationContext context
    ) {

        if (context.getRequest().getDueDate() != null &&
                context.getRequest()
                        .getDueDate()
                        .isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {

            throw new RuntimeException(
                    "Due date cannot be in the past"
            );
        }
    }
}