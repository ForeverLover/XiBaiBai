package com.jph.xibaibai.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jph on 2015/9/7.
 */
public class AllAddress implements Serializable {
    private static final long serialVersionUID = 1746675606021548148L;

    private List<Address> list;
    @JSONField(name = "default")
    private int defaultId;

    public List<Address> getList() {
        return list;
    }

    public void setList(List<Address> list) {
        this.list = list;
    }

    public int getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(int defaultId) {
        this.defaultId = defaultId;
    }

    public int getPosition(int type) {
        if (list == null || list.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAddress_type() == type) {
                return i;
            }
        }

        return -1;
    }

    public Address getHomeAddress() {
        int homePosition = getPosition(0);
        if (homePosition == -1) {
            return null;
        }
        return list.get(homePosition);
    }

    public Address getCompanyAddress() {

        int companyPosition = getPosition(1);
        if (companyPosition == -1) {
            return null;
        }
        return list.get(companyPosition);
    }

    public void setHomeAddress(Address address) {
        int homePosition = getPosition(0);
        if (homePosition == -1) {
            address.setAddress_type(0);
            if (list == null) {
                list = new ArrayList<Address>();
            }
            list.add(address);
            return;
        }
        list.set(homePosition, address);
    }


    public void setCompanyAddress(Address address) {
        int CompanyPosition = getPosition(1);
        if (CompanyPosition == -1) {
            address.setAddress_type(1);
            if (list == null) {
                list = new ArrayList<Address>();
            }
            list.add(address);
            return;
        }
        list.set(CompanyPosition, address);
    }
}
