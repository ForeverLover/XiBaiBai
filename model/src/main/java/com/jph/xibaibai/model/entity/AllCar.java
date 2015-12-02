package com.jph.xibaibai.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jph on 2015/8/22.
 */
public class AllCar implements Serializable {

    private static final long serialVersionUID = -4865439280081308903L;

    private List<Car> list = new ArrayList<>();
    @JSONField(name = "default")
    private int defaultId;

    public List<Car> getList() {
        return list;
    }

    public void setList(List<Car> list) {
        this.list = list;
    }

    public int getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(int defaultId) {
        this.defaultId = defaultId;
    }

    /**
     * 得到默认车辆
     *
     * @return
     */
    public Car getDefaultCar() {
        if (list.isEmpty()) {
            return null;
        }

        for (Car car : list) {
            if (car.getId() == defaultId) {
                return car;
            }
        }

        return null;
    }
}
