package com.leoalelui.ticketsystem.persistence.enums;

/**
 * Enum para las prioridades de los tickets
 * 
 * @author Leonardo Argoty
 */
public enum Priority {
    BAJA("Baja"),
    MEDIA("Media"),
    ALTA("Alta"),
    CRITICA("Critica");

    private final String displayName;

    Priority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
