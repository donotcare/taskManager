package ru.techport.task.manager.ui.task;


import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.techport.task.manager.backend.task.TaskService;
import ru.techport.task.manager.backend.user.UserService;

@HtmlImport("styles/shared-styles.html")
@Route(value = "task")
public class TaskView extends FormLayout implements HasUrlParameter<String> {
    @Autowired
    private TaskService service;
    @Autowired
    private UserService userService;

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Dialog dialog = new TaskEditDialog(userService, service, service.getTaskWithComments(Integer.valueOf(parameter)));
        dialog.addDetachListener(e -> this.getUI().ifPresent(ui -> ui.navigate("task")));
        dialog.open();
    }
}
