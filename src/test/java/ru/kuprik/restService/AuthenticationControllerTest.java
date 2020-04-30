package ru.kuprik.restService;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kuprik.restService.dto.AuthenticationRequestDTO;
import ru.kuprik.restService.dto.ClientDTO;
import ru.kuprik.restService.dto.ClientRegestrationDTO;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(properties = {"jdbc:postgresql://localhost:5432/test_db"},
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    // Вход с корректными логином и паролем
    @Test
    public void correctAuthetication() {
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();

        authenticationRequestDTO.setUsername("dima_kuprik");
        authenticationRequestDTO.setPassword("test");

        ResponseEntity<AuthenticationRequestDTO> responseEntity = testRestTemplate.postForEntity("/api/auth/login", authenticationRequestDTO, AuthenticationRequestDTO.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    // Вход с некоректным логином
    @Test
    public void incorrectLoginAuthentication() {
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();

        authenticationRequestDTO.setUsername("dima_kuprikaaaaaa");
        authenticationRequestDTO.setPassword("test");

        ResponseEntity<AuthenticationRequestDTO> responseEntity = testRestTemplate.postForEntity("/api/auth/login", authenticationRequestDTO, AuthenticationRequestDTO.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    // Вход с некорректным паролем
    @Test
    public void incorrectPasswordAuthentication() {
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();

        authenticationRequestDTO.setUsername("dima_kuprik");
        authenticationRequestDTO.setPassword("test22222");

        ResponseEntity<AuthenticationRequestDTO> responseEntity = testRestTemplate.postForEntity("/api/auth/login", authenticationRequestDTO, AuthenticationRequestDTO.class);

        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    //Добавление нового пользователя
    @Test
    public void createUser() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setLogin("login_login");
        clientDTO.setFirstName("login");
        clientDTO.setSecondName("login");
        clientDTO.setMiddleName("login");

        String password = "test";

        ClientRegestrationDTO clientRegestrationDTO = new ClientRegestrationDTO();
        clientRegestrationDTO.setClientDTO(clientDTO);
        clientRegestrationDTO.setPassword(password);

        ResponseEntity<ClientDTO> responseEntity = testRestTemplate.postForEntity("/api/auth/regestrarion", clientRegestrationDTO, ClientDTO.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    //Добавление уже существующего пользователя
    @Test
    public void createAlredyCreatedClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setLogin("dima_kuprik");
        clientDTO.setFirstName("login");
        clientDTO.setSecondName("login");
        clientDTO.setMiddleName("login");

        String password = "test";

        ClientRegestrationDTO clientRegestrationDTO = new ClientRegestrationDTO();
        clientRegestrationDTO.setClientDTO(clientDTO);
        clientRegestrationDTO.setPassword(password);

        ResponseEntity<ClientDTO> responseEntity = testRestTemplate.postForEntity("/api/auth/regestrarion", clientRegestrationDTO, ClientDTO.class);

        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }


}
