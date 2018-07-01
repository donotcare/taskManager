package ru.techport.task.manager.ui.feed;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.techport.task.manager.backend.message.EventMessage;
import ru.techport.task.manager.backend.message.MessageService;
import ru.techport.task.manager.backend.user.User;
import ru.techport.task.manager.backend.user.UserService;
import ru.techport.task.manager.ui.MainView;

@HtmlImport("styles/shared-styles.html")
@Route(value = "message", layout = MainView.class)
public class TabEvent extends VerticalLayout {
    private final Grid<EventMessage> grid = new Grid<>();
    private final MessageService service;
    private final ComboBox<User> authorCombo = new ComboBox<>();

    @Autowired
    public TabEvent(MessageService service, UserService userService) {
        this.service = service;
        authorCombo.setItems(userService.getCurrentUser());
        authorCombo.setValue(userService.getCurrentUser());
        authorCombo.setEnabled(false);
        HorizontalLayout filtering = new HorizontalLayout(authorCombo);

        grid.setSizeFull();

        grid.addColumn(EventMessage::getAuthor).setHeader("Автор");
        grid.addColumn(EventMessage::getMessage).setHeader("Сообщение");

        HorizontalLayout main = new HorizontalLayout(grid);
        main.setAlignItems(FlexComponent.Alignment.START);
        main.setSizeFull();
        add(filtering, main);
        setHeight("100vh");
        updateList(userService.getCurrentUser());
    }

    public void updateList(User user) {
        grid.setItems(service.getMessagesByAuthor(user));
    }
}

