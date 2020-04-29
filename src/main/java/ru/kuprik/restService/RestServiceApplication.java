package ru.kuprik.restService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ru.kuprik.restService.service.impl.RegestrationServiceIMPL;

@SpringBootApplication
public class RestServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}
}
