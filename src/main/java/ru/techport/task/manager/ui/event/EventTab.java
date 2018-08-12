package ru.techport.task.manager.ui.event;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.techport.task.manager.backend.message.EventMessage;
import ru.techport.task.manager.backend.message.EventType;
import ru.techport.task.manager.backend.message.MessageService;
import ru.techport.task.manager.backend.user.User;
import ru.techport.task.manager.backend.user.UserService;
import ru.techport.task.manager.ui.MainView;


@Route(value = "message", layout = MainView.class)
public class EventTab extends VerticalLayout {
    private final Grid<EventMessage> grid = new Grid<>();
    private final MessageService service;
    private final ComboBox<User> authorCombo = new ComboBox<>();

    @Autowired
    public EventTab(MessageService service, UserService userService) {
        this.service = service;
        authorCombo.setItems(userService.getCurrentUser());
        authorCombo.setValue(userService.getCurrentUser());
        authorCombo.setEnabled(false);
        HorizontalLayout filtering = new HorizontalLayout(authorCombo);

        grid.setSizeFull();

        grid.addColumn(EventMessage::getAuthor).setHeader("Автор");
        grid.addColumn(EventMessage::getMessage).setHeader("Сообщение");
        grid.addColumn(new ComponentRenderer<>(this::createRetryButton));

        HorizontalLayout main = new HorizontalLayout(grid);
        main.setAlignItems(FlexComponent.Alignment.START);
        main.setSizeFull();
        add(filtering, main);
        setHeight("100vh");
        updateList(userService.getCurrentUser());
    }

    public Button createRetryButton(EventMessage eventMessage){
        Button button = new Button("Повторить");
        if(eventMessage.getType() == EventType.RETRY) {
            button.addClickListener(e -> service.retryEvent(eventMessage.getId(), eventMessage.getClassName()));
        } else {
            button.setVisible(false);
        }
        return button;
    }

    public void updateList(User user) {
        grid.setItems(service.getMessagesByAuthor(user));
    }
}

