package ru.techport.task.manager.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import ru.techport.task.manager.ui.feed.TabEvent;
import ru.techport.task.manager.ui.task.TabTask;


@Route
@PageTitle("Задачи")
public class MainView extends VerticalLayout implements RouterLayout {
    private final String taskLabel = "Мои задачи";
    private final String messagesLabel = "Лента";
    private final Tab taskTab = new Tab(taskLabel);
    private final Tab messagesTab = new Tab(messagesLabel);

    public MainView() {
        Tabs tabs = new Tabs();
        tabs.add(taskTab, messagesTab);
        tabs.addSelectedChangeListener(event -> tabs.getUI().ifPresent(ui -> navigate(event.getSource().getSelectedTab(), ui)));
        HorizontalLayout tabsLayout = new HorizontalLayout(tabs);
        add(tabsLayout);
        tabs.setSelectedTab(taskTab);
    }

    private void navigate(Tab tab, UI ui) {
        switch (tab.getLabel()) {
            case taskLabel:
                ui.navigate(TabTask.class);
                break;
            case messagesLabel:
                ui.navigate(TabEvent.class);
                break;
        }
    }

}
