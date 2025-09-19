package com.leoalelui.ticketsystem.persistence.enums;

/**
 * Enum para los estados de los tickets
 * 
 * @author Leonardo Argoty
 */
public enum State {
    ABIERTO("Abierto"),
    EN_PROGRESO("En progreso"),
    RESUELTO("Resuelto"),
    CERRADO("Cerrado");

    private final String displayName;

    State(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
