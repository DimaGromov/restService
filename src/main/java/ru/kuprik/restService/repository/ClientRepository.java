package ru.kuprik.restService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kuprik.restService.model.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // Поиск клиента по логину
    Client findClientByLogin(String login);

    // Поиск клиента по ID
    Client findClientById(Long id);

}
