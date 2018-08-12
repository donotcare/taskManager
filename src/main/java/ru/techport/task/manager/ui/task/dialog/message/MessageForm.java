package ru.techport.task.manager.ui.task.dialog.message;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.techport.task.manager.backend.comment.CommentService;
import ru.techport.task.manager.backend.task.Task;

import java.time.LocalDateTime;

public class MessageForm extends VerticalLayout {
    private final Task task;
    private final CommentService service;
    private final MessageList messageList = new MessageList();
    private LocalDateTime lastUpdate;

    public MessageForm(Task task, CommentService service) {
        this.task = task;
        this.service = service;
        this.lastUpdate = LocalDateTime.now();
        if(task.getId() != null) {
            add(messageList, createMessageLayout());
            getComments();
        }
    }

    private HorizontalLayout createMessageLayout() {
        HorizontalLayout inputLayout = new HorizontalLayout();
        TextField messageField = new TextField();
        Button sendButton = new Button("Отправить");
        sendButton.addClickListener(e -> {
            service.addComment(task, messageField.getValue());
            service.getCommentsSinceDate(task, lastUpdate).forEach(c -> messageList.add(new MessageLayout(c)));
            lastUpdate = LocalDateTime.now();
            messageField.clear();
        });
        inputLayout.setWidth("100%");
        inputLayout.add(messageField, sendButton);
        inputLayout.expand(messageField);

        messageField.setPlaceholder("Введите сообщение ...");
        sendButton.getElement().getThemeList().add("primary");

        return inputLayout;
    }

    private void getComments() {
        service.getCommentsByTask(task).forEach(c -> messageList.add(new MessageLayout(c)));
    }
}
