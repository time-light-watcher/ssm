package com.qf.bank.service;

import com.qf.bank.pojo.User;

public interface UserService {
    User login(Long cardNumber);

    String showMoney(Integer id);

    String transfer(Long cardNumberFrom, Long cardNumberTo, Double moneyTransfer);

    String changePassword(Integer id,Integer passwordOld,Integer passwordNew);
}
