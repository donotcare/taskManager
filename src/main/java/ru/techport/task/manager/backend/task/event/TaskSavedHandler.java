package ru.techport.task.manager.backend.task.event;

import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.techport.task.manager.backend.message.NewMessageEvent;
import ru.techport.task.manager.backend.message.RetriableEventHandler;
import ru.techport.task.manager.backend.notification.Notification;
import ru.techport.task.manager.backend.notification.NotificationRepository;
import ru.techport.task.manager.backend.task.Task;
import ru.techport.task.manager.backend.task.TaskRepository;
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
public class TaskSavedHandler extends RetriableEventHandler<TaskSavedEvent> {
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TaskNotificationRepository taskNotificationRepository;

    @Override
    public void handleEvent(TaskSavedEvent event) {
        Task task = event.getTask();
        MultiFileMemoryBuffer filesBuffer = event.getFilesBuffer();
        Set<String> files = filesBuffer.getFiles();
        task.setFiles(files);
        taskRepository.save(task);
        List<Notification> notifications = event.getNotifications();

        saveNotifications(task, notifications);
        saveFilesToDisk(task.getId(), filesBuffer, files);

        publisher.publishEvent(new NewMessageEvent("Saved " + event.getTask()));
        throw new RuntimeException("eerrrr");
    }

    private void saveNotifications(Task task, List<Notification> notifications) {
        notificationRepository.saveAll(notifications);
        List<TaskNotification> taskNotifications = notifications.stream().map(n -> TaskNotification.of(task, n)).collect(Collectors.toList());
        taskNotificationRepository.saveAll(taskNotifications);
    }

    private void saveFilesToDisk(long taskId, MultiFileMemoryBuffer filesBuffer, Set<String> files) {
        for (String file : files) {
            ByteArrayOutputStream os = filesBuffer.getOutputBuffer(file);
            Path path = Paths.get(String.format("C:\\data\\task%s\\%s", taskId, file));
            try {
                Files.write(path, os.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
