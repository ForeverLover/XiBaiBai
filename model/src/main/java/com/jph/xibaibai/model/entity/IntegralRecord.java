package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 积分记录
 * Created by jph on 2015/9/29.
 */
public class IntegralRecord implements Serializable{
    private static final long serialVersionUID = 915928212049934992L;
    private int id;
    private int uid;
    private int points;//积分数量
    private String point_name;
    private long time;
    private int type;//1 收入积分   2 支出积分

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
