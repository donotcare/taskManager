package ru.techport.task.manager.backend.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.techport.task.manager.backend.task.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getCommentsByTask(Task task);

    List<Comment> getCommentsByTaskAndCreatedAfter(Task task, LocalDateTime localDateTime);
}
