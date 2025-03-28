package org.example.booktracker.evenListener;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailEvent extends ApplicationEvent {
    private final String message;
    private final String emailTo;

    public EmailEvent(Object source, String message, String emailTo) {
        super(source);
        this.message = message;
        this.emailTo = emailTo;
    }
}
