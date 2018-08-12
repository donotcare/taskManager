package ru.techport.task.manager.backend.comment;

import org.springframework.stereotype.Service;
import ru.techport.task.manager.backend.task.Task;
import ru.techport.task.manager.backend.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository repository;
    private final UserService userService;

    public CommentService(CommentRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public void addComment(Task task, String text) {
        Comment comment = new Comment(userService.getCurrentUser(), task, text);
        repository.save(comment);
    }

    public List<Comment> getCommentsSinceDate(Task task, LocalDateTime dateTime) {
        return repository.getCommentsByTaskAndCreatedAfter(task, dateTime);
    }

    public List<Comment> getCommentsByTask(Task task) {
        return repository.getCommentsByTask(task);
    }
}
