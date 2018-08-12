package ru.techport.task.manager.backend.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.techport.task.manager.backend.user.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RetryEventHandler implements ApplicationListener<RetryEvent> {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RetryEvent event) {
        Path path = getMessagePath(event.getEventId());
        try(Stream<String> fileStream = Files.lines(path)) {
            String data = fileStream.collect(Collectors.joining());
            Object originalEvent = new ObjectMapper().readValue(data, Class.forName(event.getClassName()));
            messageService.fireEvent(originalEvent);
        } catch (Exception e) {
            messageService.save(new EventMessage(userService.getCurrentUser(), EventType.ERROR, event.getClass().getName(),"Ошибка " + e.getMessage()));
        }
    }

    private Path getMessagePath(long id) {
        return Paths.get(String.format("C:\\data\\messages\\%s.json", id));
    }
}
