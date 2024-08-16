package com.shree.communityconnect.constants;

public enum ActivityCategory {

    SOCIAL("Social"),
    SPORTS("Sports"),
    FITNESS("Fitness"),
    FUNDRAISERS("Fundraisers"),
    CLEAN_UP("Clean Up");

    private final String displayName;

    ActivityCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
