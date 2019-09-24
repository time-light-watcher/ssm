package com.qf.bank.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private Long cardNumber;
    private Integer password;
    private Double money;
    private String frozen;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cardNumber=" + cardNumber +
                ", password=" + password +
                ", money=" + money +
                ", frozen='" + frozen + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }
}
