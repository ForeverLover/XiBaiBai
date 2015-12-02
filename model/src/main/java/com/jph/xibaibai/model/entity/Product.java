package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * Created by jph on 2015/8/16.
 */
public class Product implements Serializable {
    private static final long serialVersionUID = -4832198051935101542L;

    private int id;//	产品id	Int
    private String p_name;//	产品名	String
    private double p_price;//产品价格	String
    private String p_info;//产品详细介绍	String

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public double getP_price() {
        return p_price;
    }

    public void setP_price(double p_price) {
        this.p_price = p_price;
    }

    public String getP_info() {
        return p_info;
    }

    public void setP_info(String p_info) {
        this.p_info = p_info;
    }
}
