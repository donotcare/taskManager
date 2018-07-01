package ru.techport.task.manager.backend.task.notification;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.techport.task.manager.backend.task.Task;

import java.util.List;

public interface TaskNotificationRepository extends JpaRepository<TaskNotification, Long> {
    @Query("select t from TaskNotification t where t.task = (:task)")
    List<TaskNotification> getNotificationsByTask(@Param("task") Task task);
}
