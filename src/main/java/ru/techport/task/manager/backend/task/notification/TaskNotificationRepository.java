package ru.techport.task.manager.backend.task.notification;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.techport.task.manager.backend.notification.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskNotificationRepository extends JpaRepository<TaskNotification, Long> {
    @Query("select t.notification from TaskNotification t where t.id.taskId = (:task)")
    List<Notification> getNotificationsByTask(@Param("task") long taskId);

    @Query("select t from TaskNotification t where t.notification.notificationDate <= (:dateTime)")
    List<TaskNotification> getNotificationsToSchedule(@Param("dateTime") LocalDateTime dateTime);
}
