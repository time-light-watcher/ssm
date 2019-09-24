package com.qf.bank.mapper;

import com.qf.bank.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> selectUser(User user);

    Boolean updateUser(User user);
}
