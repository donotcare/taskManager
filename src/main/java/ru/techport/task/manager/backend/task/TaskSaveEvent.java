package ru.techport.task.manager.backend.task;

import org.springframework.context.ApplicationEvent;
import ru.techport.task.manager.backend.notification.Notification;

import java.util.List;

public class TaskSaveEvent extends ApplicationEvent {
    private final List<Notification> notifications;

    public TaskSaveEvent(Task task, List<Notification> notifications) {
        super(task);
        this.notifications = notifications;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Task getTask() {
        return (Task) source;
    }
}
