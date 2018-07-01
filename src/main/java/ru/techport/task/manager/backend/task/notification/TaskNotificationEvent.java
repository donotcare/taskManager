package ru.techport.task.manager.backend.task.notification;

import org.springframework.context.ApplicationEvent;

public class TaskNotificationEvent extends ApplicationEvent {
    public TaskNotificationEvent(Object source) {
        super(source);
    }

    public String getMessage(){
        return String.format("Notificated %s", ((TaskNotification) source).getTask());
    }
}
