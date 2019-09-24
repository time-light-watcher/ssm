package com.qf.bank.service;

import com.qf.bank.pojo.Log;
import com.qf.bank.util.Page;

public interface LogService {

    Page<Log> selectLogPage(Integer userId, String dateFrom, String dateTo, Integer page);
}
