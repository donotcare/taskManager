package ru.techport.task.manager.backend.task.notification;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskNotificationScheduler {
    private final TaskNotificationRepository repository;

    public TaskNotificationScheduler(TaskNotificationRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 5000)
    public void execute() {
        System.out.println("Scheduler activated");
        List<TaskNotification> notifications = repository.getNotificationsToSchedule(LocalDateTime.now());
        notifications.forEach(this::sendNotification);
    }

    private void sendNotification(TaskNotification notification) {
        System.out.println(notification);
    }
}
