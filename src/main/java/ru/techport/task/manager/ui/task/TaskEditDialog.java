package ru.techport.task.manager.ui.task;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import ru.techport.task.manager.backend.task.Task;
import ru.techport.task.manager.backend.task.TaskService;
import ru.techport.task.manager.backend.task.comment.Comment;
import ru.techport.task.manager.backend.user.User;
import ru.techport.task.manager.backend.user.UserService;
import ru.techport.task.manager.ui.system.DateUtils;
import ru.techport.task.manager.ui.task.message.MessageLayout;
import ru.techport.task.manager.ui.task.message.MessageList;
import ru.techport.task.manager.ui.task.notification.NotificationDialog;

import java.util.ArrayList;

public class TaskEditDialog extends Dialog {


    private TaskService taskService;
    private UserService userService;
    private Task task;

    private final TextField text = new TextField("Задача");
    private final TextField taskDate = new TextField("Дата исполнения");
    private final TextField notificationDate = new TextField("Напоминание");
    private final MessageList messageList = new MessageList();
    private final ComboBox<User> authorCombo = new ComboBox("Автор");
    private final ComboBox<User> recipientCombo = new ComboBox("Исполнитель");
    private final Button save = new Button("Сохранить", e -> {
        taskService.save(task);
        close();
    });
    private final Button cancel = new Button("Выйти", e -> close());
    private Binder<Task> binder = new Binder<>(Task.class);

    public TaskEditDialog(UserService userService, TaskService taskService, Task task) {
        this.userService = userService;
        this.taskService = taskService;
        this.task = task;
        init();
    }

    public TaskEditDialog(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
        this.task = new Task(userService.getCurrentUser());
        init();
    }

    private void init() {
        setWidth("100%");
        setHeight("100%");
        text.setWidth("100%");
        authorCombo.setItems(task.getAuthor());
        authorCombo.setItemLabelGenerator(User::getName);
        authorCombo.setWidth("100%");
        recipientCombo.setItems(userService.getAll());
        recipientCombo.setItemLabelGenerator(User::getName);
        recipientCombo.setWidth("100%");

        HorizontalLayout firstLine = new HorizontalLayout(authorCombo, recipientCombo);
        firstLine.setWidth("100%");

        NotificationDialog notificationDialog = new NotificationDialog(task, new ArrayList<>());
        Button notificationButton = new Button("Настроить", e -> notificationDialog.open());

        HorizontalLayout secondLine = new HorizontalLayout(taskDate, notificationDate, notificationButton);
        secondLine.setAlignItems(FlexComponent.Alignment.BASELINE);
        secondLine.setWidth("100%");

        VerticalLayout main = new VerticalLayout();
        main.add(firstLine, secondLine, text);
        main.setSizeFull();

        VerticalLayout messageForm = new VerticalLayout();
        messageForm.add(messageList, createMessageLayout());

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        add(main, messageForm, buttons);

        bindValues();
        getComments();
    }

    private void bindValues() {
        binder.setBean(task);
        binder.forField(authorCombo).bind(Task::getAuthor, null);
        binder.forField(recipientCombo).bind(Task::getRecipient, Task::setRecipient);
        binder.forField(taskDate).bind(t -> DateUtils.dateTimeToString(t.getTaskDate()), (t, v) -> t.setTaskDate(DateUtils.stringToDateTime(v)));
        binder.bindInstanceFields(this);
    }

    private HorizontalLayout createMessageLayout() {
        HorizontalLayout inputLayout = new HorizontalLayout();
        TextField messageField = new TextField();
        Button sendButton = new Button("Отправить");
        sendButton.addClickListener(e -> {
            Comment newComment = new Comment(userService.getCurrentUser(), messageField.getValue());
            task.addComment(newComment);
            messageList.add(new MessageLayout(newComment));
            messageField.clear();
        });
        inputLayout.setWidth("100%");
        inputLayout.add(messageField, sendButton);
        inputLayout.expand(messageField);

        messageField.setPlaceholder("Введите сообщение ...");
        sendButton.getElement().getThemeList().add("primary");

        return inputLayout;
    }

    public void getComments() {
        task.getComments().forEach(c -> messageList.add(new MessageLayout(c)));
    }

}
