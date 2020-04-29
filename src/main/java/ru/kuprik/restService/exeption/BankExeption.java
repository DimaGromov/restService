package ru.kuprik.restService.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BankExeption extends RuntimeException {
    public BankExeption(String message) {
        super(message);
    }
}
