package ru.techport.task.manager.backend.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.techport.task.manager.backend.message.NewMessageEvent;

@Component
public class TaskSaveHandler implements ApplicationListener<TaskSaveEvent> {
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void onApplicationEvent(TaskSaveEvent eventMessage) {
        taskRepository.save(eventMessage.getTask());
        publisher.publishEvent(new NewMessageEvent("Saved " + eventMessage.getTask()));
    }
}
