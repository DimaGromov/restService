package ru.kuprik.restService.service.impl;


import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kuprik.restService.dto.ClientDTO;
import ru.kuprik.restService.model.Client;
import ru.kuprik.restService.model.Role;
import ru.kuprik.restService.repository.ClientRepository;
import ru.kuprik.restService.repository.RoleRepository;
import ru.kuprik.restService.service.ClientService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceIMPL implements ClientService {

    private ClientRepository clientRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;



    @Autowired
    public ClientServiceIMPL(ClientRepository clientRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClientDTO findByID(@NonNull Long id) {
        Client tempClient = clientRepository.findClientById(id);
        if(tempClient != null) {
            return ClientDTO.createClientDTO(tempClient);
        } else {
            return null;
        }
    }

    @Override
    public ClientDTO findByLogin(@NonNull String login) {
        Client tempClient = clientRepository.findClientByLogin(login);
        if(tempClient != null) {
            return ClientDTO.createClientDTO(tempClient);
        } else {
            return null;
        }
    }

    @Override
    public ClientDTO addClient(@NonNull ClientDTO clientDTO, @NonNull String password) {
        Role roleClient = roleRepository.findAllByName("ROLE_USER");
        List<Role> clientRoles = new ArrayList<>();
        clientRoles.add(roleClient);

        Client client = ClientDTO.createClient(clientDTO, passwordEncoder.encode(password));
        client.setRoles(clientRoles);
        if(clientRepository.findClientByLogin(client.getLogin()) == null) {
            clientRepository.save(client);
            return clientDTO;
        } else return null;
    }

    @Override
    public ClientDTO deleteClient(@NonNull ClientDTO clientDTO, @NonNull String password) {
        Client client = ClientDTO.createClient(clientDTO, password);
        if(clientRepository.findClientByLogin(client.getLogin()) != null) {
            clientRepository.delete(client);
            return clientDTO;
        } else return null;
    }
}
