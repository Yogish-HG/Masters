package com.project.accomatch.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
/**
 * Error Response class for Exception Handling
 * @author Ramandeep Kaur
 */
public class ErrorResponse {
    @JsonProperty("status")
    private int statusCode;

    @JsonProperty("message")
    private String errorMessage;

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date timestamp;

    public ErrorResponse(int status, String message, Date timestamp) {
        this.statusCode = status;
        this.errorMessage = message;
        this.timestamp = timestamp;
    }

}
