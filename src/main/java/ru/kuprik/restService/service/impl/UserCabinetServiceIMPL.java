package ru.kuprik.restService.service.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kuprik.restService.dto.AddMoneyDTO;
import ru.kuprik.restService.dto.CardDTO;
import ru.kuprik.restService.dto.TransactionDTO;
import ru.kuprik.restService.exeption.BankExeption;
import ru.kuprik.restService.exeption.CardAlredyCreteException;
import ru.kuprik.restService.exeption.CardNotFoundExeption;
import ru.kuprik.restService.exeption.WrongCardOwnerExeption;
import ru.kuprik.restService.service.CardService;
import ru.kuprik.restService.service.ClientService;
import ru.kuprik.restService.service.UserCabinetService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class UserCabinetServiceIMPL implements UserCabinetService {


    private ClientService clientService;
    private CardService cardService;

    @Autowired
    UserCabinetServiceIMPL(CardService cardService, ClientService clientService) {
        this.cardService = cardService;
        this.clientService = clientService;
    }

    @Override
    public CardDTO addNewCard(@NonNull CardDTO cardDTO) {
        String clientLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if(cardService.findByNumber(cardDTO.getNumber()) != null) {
            log.warn("Карта с номером: {} уже создана", cardDTO.getNumber());
            throw new CardAlredyCreteException("Карта с номером: " + cardDTO.getNumber() + " уже создана");
        }

        if(!clientLogin.equals(cardDTO.getOwnerLogin())) {
            log.warn("Карта с номером: {} не пренадлежит авторизированному пользователю", cardDTO.getNumber());
            throw new WrongCardOwnerExeption("Карта с номером: " + cardDTO.getNumber() + " не пренадлежит авторизированному пользователю");
        }
        return cardService.addNewCard(cardDTO);
    }

    @Override
    public List<CardDTO> getAllCardsByOwnerLogin() {
        String ownerLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return cardService.findAllByOwnerLogin(ownerLogin);
    }

    @Override
    public BigDecimal checkBallance(String number) {
        CardDTO cardDTO = cardService.findByNumber(number);

        if(cardDTO == null) {
            log.warn("Карта с номером: {} не найдена", number);
            throw new CardNotFoundExeption("Карта с номером: " + number + " не найдена");
        }

        return cardDTO.getRemeins();
    }

    @Transactional
    @Override
    public TransactionDTO sendMoney(String fromCard, String toCard, BigDecimal money) {

        CardDTO fromCardDTO = cardService.findByNumber(fromCard);
        CardDTO toCardDTO = cardService.findByNumber(toCard);

        String clientLogin = SecurityContextHolder.getContext().getAuthentication().getName();

        if ( fromCardDTO == null) {
            log.warn("Карта с номером: {} не найдена", fromCard);
            throw new CardNotFoundExeption("Карта с номером: " + fromCard + " не найдена");
        }

        if (toCardDTO == null) {
            log.warn("Карта с номером: {} не найдена", toCard);
            throw new CardNotFoundExeption("Карта с номером: " + toCard + " не найдена");
        }

        if(!fromCardDTO.getOwnerLogin().equals(clientLogin))  {
            log.warn("Карта с номером: {} не пренадлежит авторизированному пользователю", fromCard);
            throw new WrongCardOwnerExeption("Карта с номером: " + fromCard + " не пренадлежит авторизированному пользователю");
        }

        BigDecimal fromCardBallance = fromCardDTO.getRemeins();

        if(fromCardBallance.compareTo(money) < 0) {
            log.warn("Недостаточно средств на карте номер: {}", fromCard);
            throw new BankExeption("Недостаточно средств на карте номер: " + fromCard);
        }

        BigDecimal toCardBallance = toCardDTO.getRemeins();

        cardService.updateRemeinsByNumber(fromCardBallance.subtract(money), fromCard);
        cardService.updateRemeinsByNumber(toCardBallance.add(money), toCard);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFromCard(fromCard);
        transactionDTO.setToCard(fromCard);
        transactionDTO.setMoney(money);

        return transactionDTO;
    }

    @Override
    public CardDTO deleteCard(String number) {
        CardDTO cardDTO = cardService.findByNumber(number);

        if(cardDTO == null) {
            log.warn("Карта с номером: {} не найдена", number);
            throw new CardNotFoundExeption("Карта с номером: " + number + " не найдена");
        }

        cardService.deleteCard(cardDTO);
        return cardDTO;
    }

    @Override
    public AddMoneyDTO addMoney(String number, BigDecimal money) {

        String clientLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        CardDTO cardDTO = cardService.findByNumber(number);


        if(cardDTO == null) {
            log.warn("Карта с номером: {} не найдена", number);
            throw new CardNotFoundExeption("Карта с номером: " + number + " не найдена");
        }

        if(!cardDTO.getOwnerLogin().equals(clientLogin)) {
            log.warn("Карта с номером: {} не пренадлежит авторизированному пользователю", number);
            throw new WrongCardOwnerExeption("Карта с номером: " + number + " не пренадлежит авторизированному пользователю");
        }

        if(money.compareTo(new BigDecimal(0)) < 0) {
            log.warn("Сумма пополнения меньше нуля");
            throw new BankExeption("Сумма пополнения меньше нуля");
        }

        cardService.updateRemeinsByNumber(cardDTO.getRemeins().add(money), number);

        AddMoneyDTO addMoneyDTO = new AddMoneyDTO();
        addMoneyDTO.setCartNumber(number);
        addMoneyDTO.setMoney(money);

        return addMoneyDTO;
    }
}
