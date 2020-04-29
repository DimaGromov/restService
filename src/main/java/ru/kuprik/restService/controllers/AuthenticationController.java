package ru.kuprik.restService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuprik.restService.dto.AuthenticationRequestDTO;
import ru.kuprik.restService.dto.ClientDTO;
import ru.kuprik.restService.dto.ClientRegestrationDTO;
import ru.kuprik.restService.dto.ResponseTokenDTO;
import ru.kuprik.restService.exeption.UserAlredyRegestrateExeption;
import ru.kuprik.restService.model.Client;
import ru.kuprik.restService.security.jwt.JwtTokenProvider;
import ru.kuprik.restService.service.ClientService;
import ru.kuprik.restService.service.RegestrationService;

@RestController
@RequestMapping(value = "api/auth/")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ClientService clientService;
    private final RegestrationService regestrationService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, ClientService clientService, RegestrationService regestrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.clientService = clientService;
        this.regestrationService = regestrationService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            String username = authenticationRequestDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDTO.getPassword()));

            ClientDTO clientDTO = clientService.findByLogin(username);

            if(clientDTO == null) {
                throw new UsernameNotFoundException("Client with login: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, clientDTO.getRoles());

            ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO();
            responseTokenDTO.setUsername(username);
            responseTokenDTO.setToken(token);

            return ResponseEntity.ok(responseTokenDTO);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("regestrarion")
    public ResponseEntity<ClientDTO> regestrate(@RequestBody ClientRegestrationDTO clientRegestrationDTO) {
        ClientDTO clientDTO = clientRegestrationDTO.getClientDTO();
        String password = clientRegestrationDTO.getPassword();
        if(clientService.findByLogin(clientDTO.getLogin()) != null) {
            throw new UserAlredyRegestrateExeption("Пользователь с логином: " + clientDTO.getLogin() + " уже зарегестрирован.");
        } else {
            regestrationService.regestrateNewClient(clientDTO, password);
            return new ResponseEntity<>(clientDTO, HttpStatus.CREATED);
        }
    }


}
