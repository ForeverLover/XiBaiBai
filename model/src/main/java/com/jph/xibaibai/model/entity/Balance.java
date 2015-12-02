package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * Created by jph on 2015/9/4.
 */
public class Balance implements Serializable {
    private static final long serialVersionUID = 934758387514534910L;

    private double money;

    public double getMoney() {
        return  money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
