package com.qf.bank.service.impl;

import com.qf.bank.mapper.LogMapper;
import com.qf.bank.mapper.UserMapper;
import com.qf.bank.pojo.Log;
import com.qf.bank.pojo.User;
import com.qf.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LogMapper logMapper;

    @Override
    public User login(Long cardNumber) {
        User user = new User();
        user.setCardNumber(cardNumber);
        List<User> userList = userMapper.selectUser(user);
        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String showMoney(Integer id) {
        User user = new User();
        user.setId(id);
        List<User> userList = userMapper.selectUser(user);
        if (userList.size() > 0) {
            user = userList.get(0);
            String money = user.getMoney().toString();
            int index = money.indexOf(".") + 3;
            if (index <= money.length()) {
                money = money.substring(0, index);
            } else {
                money = money + "0";
            }
            return money;
        } else {
            return null;
        }

    }

    @Override
    public String transfer(Long cardNumberFrom, Long cardNumberTo, Double moneyTransfer) {
        User userTo = new User();
        userTo.setCardNumber(cardNumberTo);
        List<User> userList = userMapper.selectUser(userTo);
        if (userList.size() > 0) {
            userTo = userList.get(0);
            if ("Y".equals(userTo.getFrozen())) {
                return "userToFrozen";
            } else {
                User userFrom = new User();
                userFrom.setCardNumber(cardNumberFrom);
                userList = userMapper.selectUser(userFrom);
                if (userList.size() > 0) {
                    userFrom = userList.get(0);
                    if (moneyTransfer > userFrom.getMoney()) {
                        return "moneyNotEnough";
                    } else {
                        userFrom.setMoney(userFrom.getMoney() - moneyTransfer);
                        userTo.setMoney(userTo.getMoney() + moneyTransfer);
                        userFrom.setPassword(null);
                        userFrom.setCardNumber(null);
                        userFrom.setFrozen(null);
                        userTo.setPassword(null);
                        userTo.setCardNumber(null);
                        userTo.setFrozen(null);

                        Boolean updateUserFromResult = userMapper.updateUser(userFrom);
                        Boolean updateUserToResult = userMapper.updateUser(userTo);

                        Log logOut = new Log();
                        logOut.setDate(new Date(System.currentTimeMillis()));
                        logOut.setUserId(userFrom.getId());
                        logOut.setMoneyOut(moneyTransfer);
                        logOut.setResult(userFrom.getMoney());
                        logOut.setType("消费");
                        Boolean insertLogOut = logMapper.insertLog(logOut);

                        Log logIn = new Log();
                        logIn.setDate(new Date(System.currentTimeMillis()));
                        logIn.setUserId(userTo.getId());
                        logIn.setMoneyIn(moneyTransfer);
                        logIn.setResult(userTo.getMoney());
                        logIn.setType("收入");
                        Boolean insertLogIn = logMapper.insertLog(logIn);

                        if (updateUserFromResult && updateUserToResult && insertLogOut && insertLogIn) {
                            return "success";
                        }
                    }
                }
            }
        } else {
            return "noUserTo";
        }
        return null;
    }

    @Override
    public String changePassword(Integer id, Integer passwordOld, Integer passwordNew) {
        User user = new User();
        user.setId(id);
        List<User> userList = userMapper.selectUser(user);
        if (userList.size() > 0) {
            user = userList.get(0);
            if (!passwordOld.equals(user.getPassword())) {
                return "passwordOldFalse";
            } else {
                user.setCardNumber(null);
                user.setPassword(passwordNew);
                user.setFrozen(null);
                user.setMoney(null);
                Boolean result = userMapper.updateUser(user);
                if (result) {
                    return "success";
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }
}
