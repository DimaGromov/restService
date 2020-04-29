package ru.kuprik.restService.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CardNotFoundExeption extends RuntimeException {
    public CardNotFoundExeption(String message) {
        super(message);
    }
}
