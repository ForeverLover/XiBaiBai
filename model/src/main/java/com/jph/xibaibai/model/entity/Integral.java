package com.jph.xibaibai.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 积分
 * Created by jph on 2015/9/29.
 */
public class Integral implements Serializable {
    private static final long serialVersionUID = -3309760813710682026L;
    private List<IntegralRecord> list;
    private int total_points;//积分数

    public List<IntegralRecord> getList() {
        return list;
    }

    public void setList(List<IntegralRecord> list) {
        this.list = list;
    }

    public int getTotal_points() {
        return total_points;
    }

    public void setTotal_points(int total_points) {
        this.total_points = total_points;
    }
}
