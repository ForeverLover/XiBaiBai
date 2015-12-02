package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 预约时间段
 * Created by jph on 2015/8/23.
 */
public class TimeScope implements Serializable {
    private static final long serialVersionUID = 3379787342425561203L;
    private int id;
    private String time;
    private int max;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
