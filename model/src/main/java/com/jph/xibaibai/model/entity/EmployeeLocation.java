package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * Created by jph on 2015/8/16.
 */
public class EmployeeLocation implements Serializable {

    private int id;//员工id	Int
    private String current_address;//员工位置	String
    private String current_address_lg;//员工位置经度	String
    private String current_address_lt;//员工位置纬度	String

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public String getCurrent_address_lg() {
        return current_address_lg;
    }

    public void setCurrent_address_lg(String current_address_lg) {
        this.current_address_lg = current_address_lg;
    }

    public String getCurrent_address_lt() {
        return current_address_lt;
    }

    public void setCurrent_address_lt(String current_address_lt) {
        this.current_address_lt = current_address_lt;
    }
}
