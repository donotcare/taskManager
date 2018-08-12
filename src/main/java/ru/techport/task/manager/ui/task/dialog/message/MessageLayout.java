package ru.techport.task.manager.ui.task.dialog.message;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import ru.techport.task.manager.backend.comment.Comment;

import java.time.format.DateTimeFormatter;

public class MessageLayout  extends HorizontalLayout {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public MessageLayout(Comment message) {
        setWidth("100%");
        add(
                new Span(message.getCreated().format(formatter)),
                new Span(message.getAuthor() + ": "),
                new Span(message.getMessage()));
    }
}
