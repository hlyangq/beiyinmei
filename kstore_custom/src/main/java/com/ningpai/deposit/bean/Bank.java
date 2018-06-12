package com.ningpai.deposit.bean;

import java.io.Serializable;

public class Bank implements Serializable {
    private Long id;
    private String bankName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
