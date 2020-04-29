package ru.kuprik.restService.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WrongCardOwnerExeption extends RuntimeException {
    public WrongCardOwnerExeption(String message) {
        super(message);
    }
}
