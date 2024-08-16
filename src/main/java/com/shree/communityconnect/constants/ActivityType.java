package com.shree.communityconnect.constants;

public enum ActivityType {

    ONLINE("Online"),
    VENUE("Venue");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
