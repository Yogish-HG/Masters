package com.project.accomatch.Exception;

import java.sql.SQLException;

public class DataAccessException extends RuntimeException {

    public DataAccessException(String message, SQLException e) {
        super(message,e);
    }
}