package ru.kuprik.restService.service.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kuprik.restService.dto.CardDTO;
import ru.kuprik.restService.model.Card;
import ru.kuprik.restService.model.Client;
import ru.kuprik.restService.repository.CardRepository;
import ru.kuprik.restService.service.CardService;
import ru.kuprik.restService.service.ClientService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceIMPL  implements CardService {


    private CardRepository cardRepository;
    private ClientService clientService;

    @Autowired
    CardServiceIMPL(CardRepository cardRepository, ClientService clientService) {
        this.cardRepository = cardRepository;
        this.clientService = clientService;
    }

    @Override
    public CardDTO addNewCard(@NonNull CardDTO cardDTO) {

        Card card = CardDTO.createCard(cardDTO);
        if(cardRepository.findByNumber(card.getNumber()) == null) {
            cardRepository.save(card);
            return cardDTO;
        } else return null;
    }

    @Override
    public CardDTO deleteCard(@NonNull CardDTO cardDTO) {
        Card card = CardDTO.createCard(cardDTO);
        if(cardRepository.findByNumber(card.getNumber()) != null) {
            cardRepository.delete(card);
            return cardDTO;
        } else  return null;
    }

    @Override
    public CardDTO findById(@NonNull Long id) {
        Card tempCard = cardRepository.findById(id).orElse(null);
        if(tempCard != null) {
            return CardDTO.createCardDTO(tempCard);
        } else return null;
    }

    @Override
    public CardDTO findByNumber(@NonNull String number) {
        Card tempCard = cardRepository.findByNumber(number);
        if(tempCard != null) {
            return CardDTO.createCardDTO(tempCard);
        } else return null;
    }

    @Override
    public void updateRemeinsByNumber(@NonNull BigDecimal remeins, @NonNull String number) {
       Card card = cardRepository.findByNumber(number);
       card.setRemeins(remeins);
       cardRepository.save(card);
    }

    @Override
    public List<CardDTO> findAllByOwnerLogin(@NonNull String ownerLogin) {
        if(clientService.findByLogin(ownerLogin) != null) {
            List<Card> tempList = cardRepository.findAllByOwnerLogin(ownerLogin);
            List<CardDTO> listCardDTO = new ArrayList<>();
            for (Card card : tempList) {
                listCardDTO.add(CardDTO.createCardDTO(card));
            }
            return listCardDTO;
        } else return null;
    }


}
