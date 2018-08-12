package ru.techport.task.manager.ui.task;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.techport.task.manager.backend.task.Task;
import ru.techport.task.manager.backend.task.TaskService;
import ru.techport.task.manager.backend.user.User;
import ru.techport.task.manager.backend.user.UserService;
import ru.techport.task.manager.ui.MainView;
import ru.techport.task.manager.ui.task.dialog.TaskEditDialog;

@Route(value = "task", layout = MainView.class)
public class TaskTab extends VerticalLayout {
    private final Grid<Task> grid = new Grid<>();
    private ComboBox<User> recipientCombo = new ComboBox<>();
    private final TaskService service;

    @Autowired
    public TaskTab(TaskService service, UserService userService) {
        this.service = service;
        recipientCombo.setPlaceholder("Исполнитель");
        recipientCombo.setItems(userService.getAll());
        recipientCombo.addValueChangeListener(e -> updateList(e.getValue()));
        HorizontalLayout filtering = new HorizontalLayout(recipientCombo);

        Button addCustomerBtn = new Button("Добавить задачу");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            new TaskEditDialog(userService, service).open();
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);
        grid.setSizeFull();

        grid.addColumn(Task::getText).setHeader("Задача");
        grid.addColumn(Task::getStatus).setHeader("Статус");
        grid.addColumn(Task::getTaskDate).setHeader("Дата исполнения");
        grid.addColumn(Task::getRecipient).setHeader("Исполнитель");
        HorizontalLayout main = new HorizontalLayout(grid);
        main.setAlignItems(FlexComponent.Alignment.START);
        main.setSizeFull();
        add(toolbar, main);
        setHeight("100vh");
        updateList(null);

        grid.addSelectionListener(event ->
                grid.getUI().ifPresent(ui -> ui.navigate(String.format("task/%s", event.getFirstSelectedItem().map(Task::getId).map(String::valueOf).get()))));
    }

    public void updateList(User user) {
        grid.setItems(service.getTasksByRecipient(user, PageRequest.of(0, 20)).getContent());
    }
}
