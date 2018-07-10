package ru.techport.task.manager.backend.task;

import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.springframework.context.ApplicationEvent;
import ru.techport.task.manager.backend.notification.Notification;

import java.util.List;

public class TaskSaveEvent extends ApplicationEvent {
    private final List<Notification> notifications;
    private final MultiFileMemoryBuffer buffer;

    public TaskSaveEvent(Task task, List<Notification> notifications, MultiFileMemoryBuffer buffer) {
        super(task);
        this.notifications = notifications;
        this.buffer = buffer;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Task getTask() {
        return (Task) source;
    }

    public MultiFileMemoryBuffer getFilesBuffer() {
        return buffer;
    }
}
