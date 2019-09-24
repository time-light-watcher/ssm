package com.qf.bank.pojo;

import java.io.Serializable;
import java.sql.Date;

public class Log implements Serializable {
    private Integer id;
    private Date date;
    private Integer userId;
    private Double moneyOut;
    private Double moneyIn;
    private Double result;
    private String type;
    private String comment;

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", date=" + date +
                ", userId=" + userId +
                ", moneyOut=" + moneyOut +
                ", moneyIn=" + moneyIn +
                ", result=" + result +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getMoneyOut() {
        return moneyOut;
    }

    public void setMoneyOut(Double moneyOut) {
        this.moneyOut = moneyOut;
    }

    public Double getMoneyIn() {
        return moneyIn;
    }

    public void setMoneyIn(Double moneyIn) {
        this.moneyIn = moneyIn;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
