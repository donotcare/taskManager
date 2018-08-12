package ru.techport.task.manager.backend.comment;


import jdk.nashorn.internal.ir.annotations.Immutable;
import ru.techport.task.manager.backend.task.Task;
import ru.techport.task.manager.backend.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Immutable
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(nullable = false)
    private User author;
    private LocalDateTime created;
    private String message;
    @ManyToOne
    private Task task;

    public Comment() {
    }

    public Comment(User author, Task task, String message) {
        this.task = task;
        this.author = author;
        this.created = LocalDateTime.now();
        this.message = message;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getMessage() {
        return message;
    }
}
