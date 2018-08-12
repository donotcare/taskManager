package ru.techport.task.manager.backend.task;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.techport.task.manager.backend.user.User;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    Page<Task> getTasksByRecipient(User recipient, Pageable page);
}
