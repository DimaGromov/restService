package ru.kuprik.restService.dto;

public class ClientRegestrationDTO {
    private ClientDTO clientDTO;
    private String password;

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
