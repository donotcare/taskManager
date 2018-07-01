package ru.techport.task.manager.backend.message;

import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewMessageHandler implements ApplicationListener<NewMessageEvent> {
    @Autowired
    private MessageService service;

    @Override
    public void onApplicationEvent(NewMessageEvent event) {
        service.addMessage(event.getMessage());
        UI.getCurrent().getPage().setTitle("EBAAAA");
    }
}
