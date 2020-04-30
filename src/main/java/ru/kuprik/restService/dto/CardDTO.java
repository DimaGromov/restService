package ru.kuprik.restService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.NonNull;
import ru.kuprik.restService.model.Card;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDTO {


    private String number;
    private String ownerLogin;
    private BigDecimal remeins;

    public static CardDTO createCardDTO(@NotNull Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNumber(card.getNumber());
        cardDTO.setOwnerLogin(card.getOwnerLogin());
        cardDTO.setRemeins(card.getRemeins());
        return cardDTO;
    }

    public static Card createCard(@NonNull CardDTO cardDTO) {
        Card card = new Card();
        card.setNumber(cardDTO.getNumber());
        card.setOwnerLogin(cardDTO.getOwnerLogin());
        card.setRemeins(cardDTO.getRemeins());
        return card;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public BigDecimal getRemeins() {
        return remeins;
    }

    public void setRemeins(BigDecimal remeins) {
        this.remeins = remeins;
    }
}
