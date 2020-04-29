package ru.kuprik.restService.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlredyRegestrateExeption extends RuntimeException {
    public UserAlredyRegestrateExeption(String message) {
        super(message);
    }
}
