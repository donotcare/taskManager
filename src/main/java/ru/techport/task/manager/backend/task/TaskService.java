package ru.techport.task.manager.backend.task;


import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.techport.task.manager.backend.message.MessageService;
import ru.techport.task.manager.backend.notification.Notification;
import ru.techport.task.manager.backend.task.notification.TaskNotificationRepository;
import ru.techport.task.manager.backend.user.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final MessageService messageService;
    private final TaskRepository taskRepository;
    private final TaskNotificationRepository taskNotificationRepository;

    public TaskService(MessageService messageService, TaskRepository taskRepository, TaskNotificationRepository taskNotificationRepository) {
        this.messageService = messageService;
        this.taskRepository = taskRepository;
        this.taskNotificationRepository = taskNotificationRepository;
    }

    public Page<Task> getTasks(Pageable page) {
        return taskRepository.findAll(page);
    }

    public Task getTask(long id) {
        return taskRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Task getTaskWithComments(long id) {
        return taskRepository.findByIdWithComments(id).orElseThrow(RuntimeException::new);
    }

    public Page<Task> getTasksByRecipient(User recipient, Pageable page) {
        if (recipient == null) {
            return taskRepository.findAll(page);
        } else {
            return taskRepository.getTasksByRecipient(recipient, page);
        }
    }

    public void save(Task task, List<Notification> notifications, MultiFileMemoryBuffer buffer) {
        messageService.fireEvent(new TaskSaveEvent(task, notifications, buffer));
    }

    public List<Notification> getNotificationsByTask(Task task) {
        if (task.getId() == null) {
            return new ArrayList<>();
        } else {
            return taskNotificationRepository.getNotificationsByTask(task.getId());
        }
    }
}
