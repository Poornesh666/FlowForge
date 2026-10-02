package com.flowforge.command;

import org.springframework.stereotype.Component;

@Component
public class TaskCommandInvoker {

    public <T> T execute(TaskCommand<T> command) {

        return command.execute();
    }
}