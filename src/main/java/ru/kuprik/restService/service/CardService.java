package ru.kuprik.restService.service;

import ru.kuprik.restService.dto.CardDTO;
import ru.kuprik.restService.model.Card;

import java.math.BigDecimal;
import java.util.List;

public interface CardService {

    CardDTO addNewCard(CardDTO cardDTO);
    CardDTO findById(Long id);
    CardDTO findByNumber(String number);
    void updateRemeinsByNumber(BigDecimal remeins, String number);
    CardDTO deleteCard(CardDTO cardDTO);
    List<CardDTO> findAllByOwnerLogin(String ownerLogin);
}
