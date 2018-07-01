package ru.techport.task.manager.backend.task.notification;


import org.hibernate.annotations.Immutable;
import ru.techport.task.manager.backend.notification.Notification;
import ru.techport.task.manager.backend.task.Task;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Immutable
public class TaskNotification {
    @EmbeddedId
    private Id id;
    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Task task;
    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Notification notification;

    private TaskNotification(Task task, Notification notification) {
        this.task = task;
        this.notification = notification;
    }

    public Task getTask() {
        return task;
    }

    public Notification getNotification() {
        return notification;
    }

    public static TaskNotification of(Task task, Notification notification) {
        return new TaskNotification(task, notification);
    }

    @Embeddable
    private static class Id implements Serializable {
        @Column(name = "task_id")
        private long taskId;
        @Column(name = "notification_id")
        private long notificationId;

        public Id() { }

        public Id(long taskId, long notificationId) {
            this.taskId = taskId;
            this.notificationId = notificationId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return taskId == id.taskId &&
                    notificationId == id.notificationId;
        }

        @Override
        public int hashCode() {

            return Objects.hash(taskId, notificationId);
        }
    }
}
