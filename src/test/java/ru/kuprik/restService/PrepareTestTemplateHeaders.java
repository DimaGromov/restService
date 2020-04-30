package ru.kuprik.restService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.kuprik.restService.controllers.AuthenticationController;
import ru.kuprik.restService.dto.AuthenticationRequestDTO;
import ru.kuprik.restService.dto.ResponseTokenDTO;

import java.util.Collections;
import java.util.Objects;

@Component
public class PrepareTestTemplateHeaders {

    @Autowired
    private AuthenticationController authenticationController;

    public void prepareHeaders(TestRestTemplate template, String username, String password) {
        AuthenticationRequestDTO requestDto = new AuthenticationRequestDTO();
        requestDto.setUsername(username);
        requestDto.setPassword(password);
        ResponseEntity<ResponseTokenDTO> responseEntity = authenticationController.login(requestDto);

        template.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization",
                                    "Bearer_" + Objects.requireNonNull(responseEntity.getBody()).getToken());
                    return execution.execute(request, body);
                })
        );
    }

    public void cleanHeaders(TestRestTemplate template) {
        template.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .set("Authorization", "");
                    return execution.execute(request, body);
                })
        );
    }

}
