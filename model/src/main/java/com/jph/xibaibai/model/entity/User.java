package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 登录返回的用户
 * Created by jph on 2015/8/20.
 */
public class User implements Serializable {

    private int id;
    private String iphone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }
}
