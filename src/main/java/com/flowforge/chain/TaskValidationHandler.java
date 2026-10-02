package com.flowforge.chain;

public abstract class TaskValidationHandler {

    protected TaskValidationHandler next;

    public TaskValidationHandler setNext(
            TaskValidationHandler next
    ) {
        this.next = next;
        return next;
    }

    public final void handle(
            TaskValidationContext context
    ) {

        validate(context);

        if (next != null) {
            next.handle(context);
        }
    }

    protected abstract void validate(
            TaskValidationContext context
    );
}