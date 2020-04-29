package ru.kuprik.restService.dto;

import java.math.BigDecimal;

public class AddMoneyDTO {
    String cartNumber;
    BigDecimal money;

    public String getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(String cartNumber) {
        this.cartNumber = cartNumber;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
