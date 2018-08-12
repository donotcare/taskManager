package ru.techport.task.manager.backend.message;

import org.springframework.context.ApplicationEvent;

public class RetryEvent extends ApplicationEvent {
    private final long eventId;
    private final String className;

    public RetryEvent(long eventId, String className) {
        super(eventId);
        this.eventId = eventId;
        this.className = className;
    }

    public long getEventId() {
        return eventId;
    }

    public String getClassName() {
        return className;
    }
}
