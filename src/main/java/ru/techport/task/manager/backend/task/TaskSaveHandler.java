package ru.techport.task.manager.backend.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.techport.task.manager.backend.message.NewMessageEvent;
import ru.techport.task.manager.backend.notification.Notification;
import ru.techport.task.manager.backend.notification.NotificationRepository;
import ru.techport.task.manager.backend.task.notification.TaskNotification;
import ru.techport.task.manager.backend.task.notification.TaskNotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskSaveHandler implements ApplicationListener<TaskSaveEvent> {
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TaskNotificationRepository taskNotificationRepository;

    @Override
    public void onApplicationEvent(TaskSaveEvent eventMessage) {
        Task task = eventMessage.getTask();
        taskRepository.save(task);
        List<Notification> notifications = eventMessage.getNotifications();
        notificationRepository.saveAll(notifications);
        List<TaskNotification> taskNotifications = notifications.stream().map(n -> TaskNotification.of(task, n)).collect(Collectors.toList());
        taskNotificationRepository.saveAll(taskNotifications);
        publisher.publishEvent(new NewMessageEvent("Saved " + eventMessage.getTask()));
    }
}
