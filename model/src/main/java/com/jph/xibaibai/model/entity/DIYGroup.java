package com.jph.xibaibai.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * diy套餐
 * Created by jph on 2015/9/17.
 */
public class DIYGroup implements Serializable{
    private static final long serialVersionUID = 5369067017859840136L;
    private String groupName;
    private List<Integer> pro_ids;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Integer> getPro_ids() {
        return pro_ids;
    }

    public void setPro_ids(List<Integer> pro_ids) {
        this.pro_ids = pro_ids;
    }
}
