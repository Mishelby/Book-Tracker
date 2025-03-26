package org.example.booktracker.domain.User;

public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    BANNED("banned"),;

    String description;

    UserStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
