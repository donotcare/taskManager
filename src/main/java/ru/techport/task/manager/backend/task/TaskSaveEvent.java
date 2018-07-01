package ru.techport.task.manager.backend.task;

import org.springframework.context.ApplicationEvent;

public class TaskSaveEvent extends ApplicationEvent {
    private String header;

    public TaskSaveEvent(Object source) {
        super(source);
    }


    public String getHeader() {
        return header;
    }

    public Task getTask() {
        return (Task) source;
    }
}
