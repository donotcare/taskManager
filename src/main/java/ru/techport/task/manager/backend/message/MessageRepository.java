package ru.techport.task.manager.backend.message;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.techport.task.manager.backend.user.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<EventMessage, Long> {
    List<EventMessage> findEventMessageByAuthor(User author);
}
