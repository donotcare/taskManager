package ru.techport.task.manager.ui.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.techport.task.manager.backend.security.SecurityService;
import ru.techport.task.manager.ui.task.TabTask;

@Route("login")
public class Login extends VerticalLayout {
    @Autowired
    private SecurityService securityService;
    private TextField user;
    private PasswordField password;
    private Button loginButton = new Button("Login", e -> loginButtonClick());

    public Login() {
        setSizeFull();

        user = new TextField("User:");
        user.setWidth("300px");
        user.setRequiredIndicatorVisible(true);

        password = new PasswordField("Password:");
        password.setWidth("300px");
        user.setRequiredIndicatorVisible(true);
        password.setValue("");

        VerticalLayout fields = new VerticalLayout(user, password, loginButton);
        fields.setSpacing(true);
        fields.setSizeUndefined();

        VerticalLayout uiLayout = new VerticalLayout(fields);
        uiLayout.setSizeFull();

        add(uiLayout);
    }

    public void loginButtonClick() {
        boolean isAuthenficated = securityService.autoLogin(user.getValue(), password.getValue());
        if(isAuthenficated) {
            getUI().ifPresent(ui -> ui.navigate(TabTask.class));
        }
    }
}
