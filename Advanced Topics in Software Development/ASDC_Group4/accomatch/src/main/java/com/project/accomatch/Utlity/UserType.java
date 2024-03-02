package com.project.accomatch.Utlity;

public enum UserType {
    AP("AP"),
    LH("LH");

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public static UserType fromString(String userType) {
        for (UserType type : UserType.values()) {
            if (type.getUserType().equalsIgnoreCase(userType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid User Type: " + userType);
    }
}