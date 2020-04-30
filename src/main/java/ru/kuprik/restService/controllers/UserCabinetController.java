package ru.kuprik.restService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kuprik.restService.dto.AddMoneyDTO;
import ru.kuprik.restService.dto.CardDTO;
import ru.kuprik.restService.dto.TransactionDTO;
import ru.kuprik.restService.service.UserCabinetService;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/cabinet/")
public class UserCabinetController {


    private final UserCabinetService userCabinetService;



    @Autowired
    UserCabinetController(UserCabinetService userCabinetService) {
        this.userCabinetService = userCabinetService;
    }

    @GetMapping(value = "cards")
    public ResponseEntity<List<CardDTO>> getListCardDTO() {
       List<CardDTO> cardDTOList = userCabinetService.getAllCardsByOwnerLogin();

       return new ResponseEntity<>(cardDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "cards")
    public ResponseEntity<CardDTO> createDTO(@RequestBody CardDTO cardDTO) {
        CardDTO createdCardDTO = userCabinetService.addNewCard(cardDTO);

        return new ResponseEntity<>(createdCardDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "cards/{cardNumber}")
    public ResponseEntity<CardDTO> deleteCards(@PathVariable String cardNumber) {
        CardDTO deletedCardDTO = userCabinetService.deleteCard(cardNumber);

        return new ResponseEntity<>(deletedCardDTO, HttpStatus.OK);
    }

    @GetMapping(value = "cards/ballance")
    public ResponseEntity checkBallance(@RequestBody String cardNumber) {
        BigDecimal ballance = userCabinetService.checkBallance(cardNumber);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "cards/ballance")
    public ResponseEntity<AddMoneyDTO> addMoney(@RequestBody AddMoneyDTO addMoneyDTO) {
        String number = addMoneyDTO.getCartNumber();
        BigDecimal money = addMoneyDTO.getMoney();
        String clientLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        AddMoneyDTO createdAddMoneyDTO = userCabinetService.addMoney(number, money);

        return new ResponseEntity<>(createdAddMoneyDTO, HttpStatus.OK);
    }

    @PostMapping(value = "transaction")
    public ResponseEntity<TransactionDTO> sendMoney(@RequestBody TransactionDTO transactionDTO) {
        String fromCard = transactionDTO.getFromCard();
        String toCard = transactionDTO.getToCard();
        BigDecimal money = transactionDTO.getMoney();

        TransactionDTO createdTransactionDTO = userCabinetService.sendMoney(fromCard, toCard, money);

        return new ResponseEntity<>(createdTransactionDTO, HttpStatus.OK);
    }

}
