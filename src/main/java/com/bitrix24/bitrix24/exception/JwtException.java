package com.bitrix24.bitrix24.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
