package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFormulaException extends RuntimeException {
    public InvalidFormulaException() {
        super();
    }
    public InvalidFormulaException(String message) {
        super(message);
    }

}
