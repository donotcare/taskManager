package ru.techport.task.manager.backend.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import ru.techport.task.manager.backend.user.UserService;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class RetriableEventHandler<T extends ApplicationEvent> implements ApplicationListener<T> {
    private static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(T event) {
        EventMessage message = new EventMessage(userService.getCurrentUser(), EventType.IN_PROCESS,  event.getClass().getName(),"Выполняется " + event.toString());
        messageService.save(message);
        saveFile(message.getId(), event);
        try {
            handleEvent(event);
        } catch (Exception e) {
            EventMessage messageOnError = new EventMessage(userService.getCurrentUser(), EventType.RETRY, event.getClass().getName(), "Ошибка " + e.getMessage() + " сообщение " + event.toString());
            messageService.save(messageOnError);
        }

        messageService.delete(message);
    }

    private void saveFile(long id, T event) {
        try {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, event);
            Path path = getMessagePath(id);
            Files.write(path, writer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(long id) {
        Path path = getMessagePath(id);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getMessagePath(long id) {
        return Paths.get(String.format("C:\\data\\messages\\%s.json", id));
    }

    public abstract void handleEvent(T event);
}
