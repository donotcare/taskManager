package ru.techport.task.manager.backend.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getCurrentUser() {
        return repository.findById(1L).orElseThrow(RuntimeException::new);
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}
