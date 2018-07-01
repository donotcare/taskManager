package ru.techport.task.manager.backend.task.comment;


import ru.techport.task.manager.backend.user.User;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Embeddable
public class Comment {
    @OneToOne
    @JoinColumn(nullable = false)
    private User author;
    private LocalDateTime created;
    private String message;

    public Comment() {
    }

    public Comment(User author, String message) {
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
