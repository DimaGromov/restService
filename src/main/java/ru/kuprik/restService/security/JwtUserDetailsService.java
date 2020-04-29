package ru.kuprik.restService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kuprik.restService.dto.ClientDTO;
import ru.kuprik.restService.model.Client;
import ru.kuprik.restService.repository.ClientRepository;
import ru.kuprik.restService.security.jwt.JwtClient;
import ru.kuprik.restService.security.jwt.JwtUserFactory;
import ru.kuprik.restService.service.ClientService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    //private final ClientService clientService;


    private final ClientRepository clientRepository;

    @Autowired
    JwtUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
       Client client = clientRepository.findClientByLogin(login);


        if(client == null) {
            throw new UsernameNotFoundException("Пользователь с логином " + client.getLogin() + " не найден!");
        }

        JwtClient jwtClient = JwtUserFactory.create(client);
        return jwtClient;
    }
}
