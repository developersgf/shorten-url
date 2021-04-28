package com.tinyurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BadRequestException(String message) {
        super(message);
        this.status = BAD_REQUEST;
        this.message = message;
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

}
