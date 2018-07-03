package ru.techport.task.manager.backend.task;


import com.google.common.base.MoreObjects;
import ru.techport.task.manager.backend.task.comment.Comment;
import ru.techport.task.manager.backend.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(nullable = false)
    private User author;
    @OneToOne
    @JoinColumn(nullable = false)
    private User recipient;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private LocalDateTime taskDate;
    private String text;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private List<String> files = Arrays.asList("raspiska.pdf", "dogovor-prodazhi.doc", "skrin.jpeg");

    private Task() {
    }

    public Task(User author) {
        this.author = author;
        this.status = TaskStatus.ACTIVE;
        this.taskDate = LocalDateTime.now();
    }

    private Task(User author, User recipient, LocalDateTime taskDate, String text) {
        this.author = author;
        this.status = TaskStatus.ACTIVE;
        this.taskDate = taskDate;
        this.recipient = recipient;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDateTime taskDate) {
        this.taskDate = taskDate;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addFile(String file) {
        files.add(file);
    }

    public List<String> getFiles() {
        return files;
    }

    public static Task of(User author, User recipient, LocalDateTime taskDate, String text) {
        return new Task(author, recipient, taskDate, text);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("recipient", recipient)
                .add("status", status)
                .toString();
    }
}
