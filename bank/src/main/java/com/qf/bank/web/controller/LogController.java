package com.qf.bank.web.controller;

import com.qf.bank.pojo.Log;
import com.qf.bank.pojo.User;
import com.qf.bank.service.LogService;
import com.qf.bank.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    @RequestMapping("/showLog")
    @ResponseBody
    public Page<Log> showLog(String dateFrom, String dateTo, Integer page, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = ((User) session.getAttribute("user")).getId();
        Page<Log> logPage = logService.selectLogPage(userId, dateFrom, dateTo, page);
        return logPage;
    }
}
