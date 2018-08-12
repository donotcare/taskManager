package ru.techport.task.manager.backend.message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.techport.task.manager.backend.user.User;
import ru.techport.task.manager.backend.user.UserService;

import java.util.List;


@Service
public class MessageService {
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageRepository repository;

    public List<EventMessage> getMessagesByAuthor(User author) {
        return repository.findEventMessageByAuthor(author);
    }

    public void save(EventMessage message) {
        repository.save(message);
    }

    public void delete(EventMessage message) {
        repository.delete(message);
    }

    public void addMessage(String message) {
        repository.save(new EventMessage(userService.getCurrentUser(), EventType.SUCCESS, "Simple message", message));
    }

    public EventMessage getMessagebyId(long id) {
        return repository.getOne(id);
    }

    public void retryEvent(long eventId, String className) {
        fireEvent(new RetryEvent(eventId, className));
    }

    public void fireEvent(Object event) {
        publisher.publishEvent(event);
    }
}
