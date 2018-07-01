package ru.techport.task.manager.backend.task.notification;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.techport.task.manager.backend.message.MessageService;

@Component
public class TaskNotificationHandler implements ApplicationListener<TaskNotificationEvent> {
    @Autowired
    private MessageService service;

    @Override
    public void onApplicationEvent(TaskNotificationEvent event) {
        service.addMessage(event.getMessage());
    }
}
