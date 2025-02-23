package com.app.ecommerce_backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {

    private final String errorCode;

    public InvalidRequestException(String message) {
        super(message);
        this.errorCode = "INVALID_REQUEST";
    }


}
