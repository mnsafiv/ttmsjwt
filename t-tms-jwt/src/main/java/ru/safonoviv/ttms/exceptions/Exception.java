package ru.safonoviv.ttms.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class Exception {
    private int status;
    private String message;
    private Date timestamp;

    public Exception(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
