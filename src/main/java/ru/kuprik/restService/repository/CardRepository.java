package ru.kuprik.restService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.kuprik.restService.model.Card;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    // Поиск карты по номеру
    Card findByNumber(String number);

    // Поиск карт по логину владельца
    List<Card> findAllByOwnerLogin(String ownerLogin);

    // Обновлние баланса
    @Modifying
    @Query(value = "UPDATE Card c SET c.remeins = ?1 WHERE c.number = ?2")
    void updateRemeinsByNumber(BigDecimal remeins, String number);

}
