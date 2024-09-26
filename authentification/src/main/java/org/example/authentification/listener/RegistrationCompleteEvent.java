package org.example.authentification.listener;

import lombok.Getter;
import lombok.Setter;
import org.example.authentification.entites.Patient;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private Patient user;
    private String applicationUrl;

    public RegistrationCompleteEvent(Patient user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
