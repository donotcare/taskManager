package ru.techport.task.manager.ui.task.dialog.notification;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import ru.techport.task.manager.backend.notification.Notification;
import ru.techport.task.manager.backend.notification.NotificationType;
import ru.techport.task.manager.backend.task.Task;
import ru.techport.task.manager.ui.system.DateUtils;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationDialog extends Dialog {
    private final Task task;
    private final List<Notification> notifications;
    private final Grid<Notification> grid = new Grid<>();
    private final RadioButtonGroup<NotificationType> notificationType = new RadioButtonGroup<>();
    private final TextField notificationStartDate = new TextField("Дата начала");
    private final Button addNotification = new Button("Добавить");


    public NotificationDialog(Task task, List<Notification> notifications) {
        this.task = task;
        this.notifications = notifications;

        setWidth("100%");
        setHeight("100%");
        notificationType.setItems(NotificationType.values());
        notificationType.setRenderer(new TextRenderer<>(NotificationType::getName));
        HorizontalLayout newNotificationLayout = new HorizontalLayout();
        newNotificationLayout.add(notificationStartDate, addNotification);
        newNotificationLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        addNotification.addClickListener(e -> {
            notifications.add(Notification.of(DateUtils.stringToDateTime(notificationStartDate.getValue()), notificationType.getValue()));
            updateList();
        });
        grid.addColumn(t -> DateUtils.dateTimeToString(t.getNotificationDate())).setHeader("Дата");
        grid.addColumn(i -> i.getType().getName()).setHeader("Тип");
        grid.addColumn(new ComponentRenderer<>(notification ->
                new Button(new Icon(VaadinIcon.CLOSE))
        ));

        add(notificationType, newNotificationLayout, grid);
        bindValues();
        updateList();
    }

    private void bindValues() {
        notificationType.setValue(NotificationType.NONE);
        notificationStartDate.setValue(DateUtils.dateTimeToString(LocalDateTime.now().plusHours(1)));
    }

    private void updateList() {
        grid.setItems(notifications);
    }
}
