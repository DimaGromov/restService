package ru.kuprik.restService.service;

import ru.kuprik.restService.dto.AddMoneyDTO;
import ru.kuprik.restService.dto.CardDTO;
import ru.kuprik.restService.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

public interface UserCabinetService {
    CardDTO addNewCard(CardDTO cardDTO);
    List<CardDTO> getAllCardsByOwnerLogin();
    BigDecimal checkBallance(String number);
    AddMoneyDTO addMoney(String number, BigDecimal money);
    TransactionDTO sendMoney(String fromCard, String toCard, BigDecimal money);
    CardDTO deleteCard(String number);

}
