package ru.techport.task.manager.ui.task;


import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.techport.task.manager.backend.comment.CommentService;
import ru.techport.task.manager.backend.task.TaskService;
import ru.techport.task.manager.backend.user.UserService;
import ru.techport.task.manager.ui.task.dialog.TaskEditDialog;

@Route(value = "task")
public class TaskView extends FormLayout implements HasUrlParameter<String> {
    @Autowired
    private TaskService service;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Dialog dialog = new TaskEditDialog(service, userService, commentService, service.getTask(Integer.valueOf(parameter)));
        dialog.addDetachListener(e -> this.getUI().ifPresent(ui -> ui.navigate("task")));
        dialog.open();
    }
}
