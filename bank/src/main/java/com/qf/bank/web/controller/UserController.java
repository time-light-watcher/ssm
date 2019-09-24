package com.qf.bank.web.controller;

import com.qf.bank.pojo.User;
import com.qf.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/loginView")
    public String loginView() {
        return "/index";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loginResult = userService.login(user.getCardNumber());
        if (null == loginResult) {
            return "noCardNumber";
        } else if (!user.getPassword().equals(loginResult.getPassword())) {
            return "errorPassword";
        } else if ("Y".equals(loginResult.getFrozen())) {
            return "userFrozen";
        } else {
            session.setAttribute("user", loginResult);
            return "success";
        }
    }

    @RequestMapping("/bankMain")
    public String bankMain() {
        return "/bankMain";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }

    @RequestMapping("/showMoney")
    @ResponseBody
    public String showMoney(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String money = userService.showMoney(user.getId());
        return money;
    }

    @RequestMapping("/transfer")
    @ResponseBody
    public String transfer(Long cardNumberTo,Double moneyTransfer, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long cardNumberFrom = ((User) session.getAttribute("user")).getCardNumber();
        String transferResult = userService.transfer(cardNumberFrom, cardNumberTo, moneyTransfer);
        return transferResult;
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public String changePassword(Integer passwordOld, Integer passwordNew, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer id = ((User) session.getAttribute("user")).getId();
        String changePasswordResult = userService.changePassword(id, passwordOld, passwordNew);
        return changePasswordResult;
    }
}
