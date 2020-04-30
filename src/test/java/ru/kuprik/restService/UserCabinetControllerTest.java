package ru.kuprik.restService;


import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.kuprik.restService.dto.AddMoneyDTO;
import ru.kuprik.restService.dto.CardDTO;
import ru.kuprik.restService.dto.TransactionDTO;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(properties = {"jdbc:postgresql://localhost:5432/test_db"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserCabinetControllerTest {
    @Autowired
    private TestRestTemplate template;

    @Autowired
    private PrepareTestTemplateHeaders prepareTestTemplateHeaders;

    private final String username = "dima_kuprik";
    private final String password = "test";

    @BeforeAll
    public void getRestTemplateWithAuthTokenInHeadersAndInsertValuesInDataBase() {
        prepareTestTemplateHeaders.prepareHeaders(template, username, password);

    }

    @AfterAll
    public void clearHeadersAndDataBase() {
        prepareTestTemplateHeaders.cleanHeaders(template);
    }

    // Добавление карты
    @Test
    public void addCard() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNumber("0000333300003333");
        cardDTO.setOwnerLogin("dima_kuprik");
        cardDTO.setRemeins(new BigDecimal(5000.00));

        ResponseEntity<CardDTO> responseEntity = template.postForEntity("/cabinet/cards", cardDTO, CardDTO.class);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    // Добавление уже созданной карты
    @Test
    public void addAlredyCreatedCard() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNumber("1111222233334444");
        cardDTO.setOwnerLogin("dima_kuprik");
        cardDTO.setRemeins(new BigDecimal(5000.00));

        ResponseEntity<CardDTO> responseEntity = template.postForEntity("/cabinet/cards", cardDTO, CardDTO.class);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    // Добавление карты с неверным логином пользователя
    @Test
    public void addIncorrectLoginCard() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNumber("1111222233330000");
        cardDTO.setOwnerLogin("dima_kuprikaaaaaa");
        cardDTO.setRemeins(new BigDecimal(5000.00));

        ResponseEntity<CardDTO> responseEntity = template.postForEntity("/cabinet/cards", cardDTO, CardDTO.class);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    // Пополнение баланса карты
    @Test
    public void addMoney() {
        AddMoneyDTO addMoneyDTO = new AddMoneyDTO();
        addMoneyDTO.setCartNumber("1111222233334444");
        addMoneyDTO.setMoney(new BigDecimal(1000));

        ResponseEntity<AddMoneyDTO> responseEntity = template.postForEntity("/cabinet/cards/ballance/", addMoneyDTO, AddMoneyDTO.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    // Пополнение баланса не существующей карты
    @Test
    public void addMoneyIncorectCard() {
        AddMoneyDTO addMoneyDTO = new AddMoneyDTO();
        addMoneyDTO.setCartNumber("1111222233336666");
        addMoneyDTO.setMoney(new BigDecimal(1000));

        ResponseEntity<AddMoneyDTO> responseEntity = template.postForEntity("/cabinet/cards/ballance/", addMoneyDTO, AddMoneyDTO.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    // Пополнение баланса карты отрицательной суммой
    @Test
    public void addMinusMoney() {
        AddMoneyDTO addMoneyDTO = new AddMoneyDTO();
        addMoneyDTO.setCartNumber("1111222233334444");
        addMoneyDTO.setMoney(new BigDecimal(-1000));

        ResponseEntity<AddMoneyDTO> responseEntity = template.postForEntity("/cabinet/cards/ballance/", addMoneyDTO, AddMoneyDTO.class);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    // Перевод средств
    @Test
    public void sendMoney() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFromCard("1111222233334444");
        transactionDTO.setToCard("0000111100001111");
        transactionDTO.setMoney(new BigDecimal(200));

        ResponseEntity<TransactionDTO> responseEntity = template.postForEntity("/cabinet/transaction", transactionDTO, TransactionDTO.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    // Перевод средств с карты не принадлежащей авторизованному пльзователю
    @Test
    public void sendIncorectLoginNumber() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFromCard("0000111100001111");
        transactionDTO.setToCard("3333333333333333");
        transactionDTO.setMoney(new BigDecimal(200));

        ResponseEntity<TransactionDTO> responseEntity = template.postForEntity("/cabinet/transaction", transactionDTO, TransactionDTO.class);

        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

}
