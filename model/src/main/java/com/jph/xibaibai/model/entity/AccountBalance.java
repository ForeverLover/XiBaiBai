package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * Created by jph on 2015/8/16.
 */
public class AccountBalance implements Serializable {
    private double money;//余额

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
