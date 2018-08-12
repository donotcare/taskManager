package ru.techport.task.manager.backend.task.event;

import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.springframework.context.ApplicationEvent;
import ru.techport.task.manager.backend.notification.Notification;
import ru.techport.task.manager.backend.task.Task;

import java.util.List;

public class TaskSavedEvent extends ApplicationEvent {
    private final Task task;
    private final List<Notification> notifications;
    private final transient MultiFileMemoryBuffer buffer;

    public TaskSavedEvent(Task task, List<Notification> notifications, MultiFileMemoryBuffer buffer) {
        super(task);
        this.task = task;
        this.notifications = notifications;
        this.buffer = buffer;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Task getTask() {
        return task;
    }

    public MultiFileMemoryBuffer getFilesBuffer() {
        return buffer;
    }
}
