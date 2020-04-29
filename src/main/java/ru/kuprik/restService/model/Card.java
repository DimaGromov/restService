package ru.kuprik.restService.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number", length = 20, unique = true, nullable = false)
    private String number;

    @Column(name = "ownerlogin", length = 50, nullable = false)
    private String ownerLogin;

    @Column(name = "remeins", nullable = false)
    private BigDecimal remeins;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
