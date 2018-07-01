package ru.techport.task.manager.backend.notification;


import org.springframework.stereotype.Service;
import ru.techport.task.manager.backend.message.MessageService;
import ru.techport.task.manager.backend.user.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class NotificationService {
    private final MessageService service;
    private final UserService userService;

    public NotificationService(MessageService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    private final List<Notification> notifications = new ArrayList<>();

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public Collection<Notification> getNotificationsByTaskId(long taskId) {
        return notifications;
    }
}
