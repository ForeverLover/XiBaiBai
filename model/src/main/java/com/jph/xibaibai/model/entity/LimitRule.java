package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 限行规则
 * Created by jph on 2015/9/18.
 */
public class LimitRule implements Serializable{

    private String tail_number;

    public String getTail_number() {
        return tail_number;
    }

    public void setTail_number(String tail_number) {
        this.tail_number = tail_number;
    }
}
