package ru.techport.task.manager.backend.notification;

public enum NotificationType {
    NONE("Один раз"), HOUR("Каждый час"), DAY("Каждый день");

    private final String name;

    NotificationType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
