package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * Created by jph on 2015/9/7.
 */
public class Address implements Serializable ,Cloneable{
    private static final long serialVersionUID = 1605339115118493255L;

    private String address;//地址
    private String address_lt;
    private String address_lg;
    private int id;
    private int uid;
    private String address_info;//车位地址
    private int address_type;//0家1公司

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_lt() {
        return address_lt;
    }

    public void setAddress_lt(String address_lt) {
        this.address_lt = address_lt;
    }

    public String getAddress_lg() {
        return address_lg;
    }

    public void setAddress_lg(String address_lg) {
        this.address_lg = address_lg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAddress_info() {
        return address_info;
    }

    public void setAddress_info(String address_info) {
        this.address_info = address_info;
    }

    public int getAddress_type() {
        return address_type;
    }

    public void setAddress_type(int address_type) {
        this.address_type = address_type;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        Address address = null;
        try {
            address = (Address) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return address;
    }
}
