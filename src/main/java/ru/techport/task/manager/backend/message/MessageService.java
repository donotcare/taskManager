package ru.techport.task.manager.backend.message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.techport.task.manager.backend.user.User;
import ru.techport.task.manager.backend.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MessageService {
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private UserService userService;

    private final List<EventMessage> messages = new ArrayList<>();

    public List<EventMessage> getMessagesByAuthor(User author) {
        return messages.stream().filter(m -> m.getAuthor().equals(author)).collect(Collectors.toList());
    }

    public void addMessage(String message) {
        messages.add(new EventMessage(userService.getCurrentUser(), message));
    }

    public void fireEvent(Object event) {
        publisher.publishEvent(event);
    }
}
