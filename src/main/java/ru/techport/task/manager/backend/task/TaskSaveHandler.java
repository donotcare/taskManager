package ru.techport.task.manager.backend.task;

import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.techport.task.manager.backend.message.NewMessageEvent;
import ru.techport.task.manager.backend.notification.Notification;
import ru.techport.task.manager.backend.notification.NotificationRepository;
import ru.techport.task.manager.backend.task.notification.TaskNotification;
import ru.techport.task.manager.backend.task.notification.TaskNotificationRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
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
        MultiFileMemoryBuffer filesBuffer = eventMessage.getFilesBuffer();
        Set<String> files = filesBuffer.getFiles();
        task.setFiles(files);
        taskRepository.save(task);
        List<Notification> notifications = eventMessage.getNotifications();
        notificationRepository.saveAll(notifications);
        List<TaskNotification> taskNotifications = notifications.stream().map(n -> TaskNotification.of(task, n)).collect(Collectors.toList());
        taskNotificationRepository.saveAll(taskNotifications);

        for(String file : files) {
            ByteArrayOutputStream os = filesBuffer.getOutputBuffer(file);
            Path path = Paths.get(String.format("C:\\data\\task%s\\%s",task.getId(), file));
            try {
                Files.write(path, os.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        publisher.publishEvent(new NewMessageEvent("Saved " + eventMessage.getTask()));
    }
}
