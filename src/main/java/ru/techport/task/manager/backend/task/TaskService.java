package ru.techport.task.manager.backend.task;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.techport.task.manager.backend.message.MessageService;
import ru.techport.task.manager.backend.task.notification.TaskNotification;
import ru.techport.task.manager.backend.task.notification.TaskNotificationRepository;
import ru.techport.task.manager.backend.user.User;

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

    public void save(Task task) {
        messageService.fireEvent(new TaskSaveEvent(task));
    }

    public void addNotification(TaskNotification taskNotification) {
        taskNotificationRepository.save(taskNotification);
    }

    public List<TaskNotification> getNotificationsByTask(Task task) {
        return taskNotificationRepository.getNotificationsByTask(task);
    }
}
