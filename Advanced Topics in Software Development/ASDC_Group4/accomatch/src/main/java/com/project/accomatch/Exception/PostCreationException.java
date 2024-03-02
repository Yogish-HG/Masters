package com.project.accomatch.Exception;


public class PostCreationException extends RuntimeException {

    public PostCreationException(String message) {
        super(message);
    }

    public PostCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
