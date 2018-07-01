package ru.techport.task.manager.backend.message;


import ru.techport.task.manager.backend.user.User;

public class EventMessage {
    private final User author;
    private final String message;

    public EventMessage(User author, String message) {
        this.author = author;
        this.message = message;
    }

    public User getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
