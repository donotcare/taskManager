package ru.techport.task.manager.backend.message;

import org.springframework.context.ApplicationEvent;

public class NewMessageEvent extends ApplicationEvent {

    public NewMessageEvent(Object source) {
        super(source);
    }

    public String getMessage() {
        return (String) source;
    }
}
