package ru.kuprik.restService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;
import ru.kuprik.restService.model.Client;
import ru.kuprik.restService.model.Role;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO {
    private String login;
    private String firstName;
    private String secondName;
    private String middleName;
    private List<Role> Roles;

    public static ClientDTO createClientDTO(@NonNull Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setLogin(client.getLogin());
        clientDTO.setFirstName(client.getFirstName());
        clientDTO.setSecondName(client.getSecondName());
        clientDTO.setMiddleName(client.getMiddleName());
        clientDTO.setRoles(client.getRoles());
        return clientDTO;
    }

    public static Client createClient(@NonNull ClientDTO clientDTO, @NonNull String password) {
        Client client = new Client();
        client.setLogin(clientDTO.getLogin());
        client.setFirstName(clientDTO.getFirstName());
        client.setSecondName(clientDTO.getSecondName());
        client.setMiddleName(clientDTO.getMiddleName());
        client.setPassword(password);
        client.setRoles(clientDTO.getRoles());
        return client;
    }



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public List<Role> getRoles() {
        return Roles;
    }

    public void setRoles(List<Role> clientRoles) {
        this.Roles = clientRoles;
    }
}
