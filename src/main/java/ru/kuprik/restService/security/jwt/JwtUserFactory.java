package ru.kuprik.restService.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.kuprik.restService.model.Client;
import ru.kuprik.restService.model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtClient create(Client client) {
        return new JwtClient(
                client.getId(),
                client.getLogin(),
                client.getFirstName(),
                client.getSecondName(),
                client.getMiddleName(),
                client.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(client.getRoles())));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> clientRoles) {
        return clientRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }

}
