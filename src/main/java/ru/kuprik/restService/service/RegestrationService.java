package ru.kuprik.restService.service;

import ru.kuprik.restService.dto.ClientDTO;
import ru.kuprik.restService.model.Client;

public interface RegestrationService {
    ClientDTO regestrateNewClient(ClientDTO clientDTO, String password);
    ClientDTO deleteClient(ClientDTO clientDTO);
}
