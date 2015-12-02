package com.jph.xibaibai.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * diy数据
 * Created by jph on 2015/9/17.
 */
public class DIYData implements Serializable {
    private static final long serialVersionUID = -4926139733820444831L;
    private List<DIYGroup> group;
    private List<DIYProduct> list;

    public List<DIYGroup> getGroup() {
        return group;
    }

    public void setGroup(List<DIYGroup> group) {
        this.group = group;
    }

    public List<DIYProduct> getList() {
        return list;
    }

    public void setList(List<DIYProduct> list) {
        this.list = list;
    }
}
