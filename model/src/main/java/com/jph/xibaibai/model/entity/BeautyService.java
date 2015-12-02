package com.jph.xibaibai.model.entity;

import java.util.List;

/**
 * Created by Eric on 2015/11/9.
 * 美容服务项目
 */
public class BeautyService {
    /**DIY套餐的封装*/
    private List<DIYMeals> diyMealsList;
    /**打蜡的种类的封装*/
    private List<BeautyItemProduct> waxList;
    /**DIY子项服务的封装*/
    private List<BeautyItemProduct> diyItemList;
    /**非必须洗车服务的封装*/
    private List<BeautyItemProduct> notWashList;

    public List<DIYMeals> getDiyMealsList() {
        return diyMealsList;
    }

    public void setDiyMealsList(List<DIYMeals> diyMealsList) {
        this.diyMealsList = diyMealsList;
    }

    public List<BeautyItemProduct> getWaxList() {
        return waxList;
    }

    public void setWaxList(List<BeautyItemProduct> waxList) {
        this.waxList = waxList;
    }

    public List<BeautyItemProduct> getDiyItemList() {
        return diyItemList;
    }

    public void setDiyItemList(List<BeautyItemProduct> diyItemList) {
        this.diyItemList = diyItemList;
    }

    public List<BeautyItemProduct> getNotWashList() {
        return notWashList;
    }

    public void setNotWashList(List<BeautyItemProduct> notWashList) {
        this.notWashList = notWashList;
    }
}
