package com.jph.xibaibai.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏 on 2015/11/6.
 */
public class OverlyCoordinate {

    private List<PolugonCoord> circleList;

    private List<ArrayList<PolugonCoord>> allCityList;

    public List<ArrayList<PolugonCoord>> getAllCityList() {
        return allCityList;
    }

    public void setAllCityList(List<ArrayList<PolugonCoord>> allCityList) {
        this.allCityList = allCityList;
    }

    public List<PolugonCoord> getCircleList() {
        return circleList;
    }

    public void setCircleList(List<PolugonCoord> circleList) {
        this.circleList = circleList;
    }

}
