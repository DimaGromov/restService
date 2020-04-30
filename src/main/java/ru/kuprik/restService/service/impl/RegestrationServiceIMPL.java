package ru.kuprik.restService.service.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kuprik.restService.dto.ClientDTO;
import ru.kuprik.restService.model.Client;
import ru.kuprik.restService.repository.ClientRepository;
import ru.kuprik.restService.service.ClientService;
import ru.kuprik.restService.service.RegestrationService;

@Service
public class RegestrationServiceIMPL implements RegestrationService {

    @Autowired
    private ClientService clientService;

    RegestrationServiceIMPL(ClientService clientService) {
        this.clientService = clientService;
    }

    public ClientDTO regestrateNewClient(@NonNull ClientDTO clientDTO, @NonNull String password) {
        return clientService.addClient(clientDTO, password);
    }

    public ClientDTO deleteClient(@NonNull ClientDTO clientDTO) {
        return clientService.deleteClient(clientDTO);
    }

}
