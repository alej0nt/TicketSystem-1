package com.leoalelui.ticketsystem.persistence.enums;

/**
 * Enum para los diferentes roles de los empleados
 * 
 * @author Leonardo Argoty
 */
public enum Role {
    USER("User"),
    ADMIN("Admin"),
    AGENT("Agent");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}