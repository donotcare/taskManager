package ru.techport.task.manager.backend.task;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.techport.task.manager.backend.user.User;

import java.util.Optional;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    Page<Task> getTasksByRecipient(User recipient, Pageable page);
    @Query("select t from Task t left join fetch t.comments where t.id = (:id)")
    Optional<Task> findByIdWithComments(@Param("id") long id);
}
