package ru.kuprik.restService.service;

import ru.kuprik.restService.dto.ClientDTO;
import ru.kuprik.restService.model.Client;

public interface ClientService {
    ClientDTO findByID(Long ID);
    ClientDTO findByLogin(String Login);
    ClientDTO addClient(ClientDTO clientDTO, String password);
    ClientDTO deleteClient(ClientDTO clientDTO, String password);
}
