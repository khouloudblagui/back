package org.example.authentification.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("management:read"),
    MANAGER_CREATE("management:create"),
    MANAGER_UPDATE("management:update"),
    MANAGER_DELETE("management:delete"),

    DOCTOR_READ("doctor:read"),
    DOCTOR_CREATE("doctor:create"),
    DOCTOR_UPDATE("doctor:update"),
    DOCTOR_DELETE("doctor:delete"),

    PATIENT_READ("patient:read"),
    PATIENT_CREATE("patient:create"),
    PATIENT_UPDATE("patient:update"),
    PATIENT_DELETE("patient:delete"),

    NURSE_READ("nurse:read"),
    NURSE_CREATE("nurse:create"),
    NURSE_UPDATE("nurse:update"),
    NURSE_DELETE("nurse:delete")


    ;

    @Getter
    private final String permission;

}

