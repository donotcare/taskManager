package ru.techport.task.manager.backend.message;


import org.hibernate.annotations.Immutable;
import ru.techport.task.manager.backend.user.User;

import javax.persistence.*;

@Entity
@Immutable
public class EventMessage {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(nullable = false)
    private User author;
    private String message;
    @Enumerated(EnumType.STRING)
    private EventType type;
    private String className;

    public EventMessage() {
    }

    public EventMessage(User author, EventType type, String className, String message) {
        this.author = author;
        this.type = type;
        this.className = className;
        this.message = message;
    }

    public User getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }

    public EventType getType() {
        return type;
    }

    public String getClassName() {
        return className;
    }
}
