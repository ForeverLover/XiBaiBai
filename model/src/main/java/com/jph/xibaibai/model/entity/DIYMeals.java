package com.jph.xibaibai.model.entity;

import java.util.List;

/**
 * Created by Eric on 2015/11/9.
 * DIY套餐
 */
public class DIYMeals {
    /**套餐名称*/
    private String groupName;
    /**套餐中包含的DIY产品服务*/
    private List<String> diyMealsId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getDiyMealsId() {
        return diyMealsId;
    }

    public void setDiyMealsId(List<String> diyMealsId) {
        this.diyMealsId = diyMealsId;
    }
}
