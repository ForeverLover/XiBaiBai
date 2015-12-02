package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 充值信息
 * Created by jph on 2015/9/20.
 */
public class TopUpInfo implements Serializable{
    private static final long serialVersionUID = -7325501455822717104L;
    private String recharge_num;
    private String recharge_name;
    private double recharge_price;
    private int recharge_type;

    public String getRecharge_num() {
        return recharge_num;
    }

    public void setRecharge_num(String recharge_num) {
        this.recharge_num = recharge_num;
    }

    public String getRecharge_name() {
        return recharge_name;
    }

    public void setRecharge_name(String recharge_name) {
        this.recharge_name = recharge_name;
    }

    public double getRecharge_price() {
        return recharge_price;
    }

    public void setRecharge_price(double recharge_price) {
        this.recharge_price = recharge_price;
    }

    public int getRecharge_type() {
        return recharge_type;
    }

    public void setRecharge_type(int recharge_type) {
        this.recharge_type = recharge_type;
    }
}
