package ru.techport.task.manager.backend;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.techport.task.manager.backend.task.Task;
import ru.techport.task.manager.backend.task.TaskRepository;
import ru.techport.task.manager.backend.user.User;
import ru.techport.task.manager.backend.user.UserRepository;

import java.time.LocalDateTime;

@Service
public class AppInitializer {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public AppInitializer(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    public void init(ApplicationReadyEvent event) {
        User userOne = new User("Ivanov Ivan", "zhmylevaa@gmail.com");
        userRepository.save(userOne);
        User userTwo = new User("Petrov Petr", "zhmylevaa@gmail.com");
        userRepository.save(userTwo);
        Task firstTask = Task.of(userOne, userTwo, LocalDateTime.now(), "Передать накладную");
        taskRepository.save(firstTask);
        Task secondTask = Task.of(userTwo, userOne, LocalDateTime.now(), "Передать накладную");
        taskRepository.save(secondTask);
    }
}
