package com.project.accomatch.Utlity;

public enum ResponseStatus {
    SUCCESS("Success"),
    ERROR("Error");

    private final String message;

    ResponseStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
