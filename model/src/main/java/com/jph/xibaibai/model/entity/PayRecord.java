package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 资金记录
 * Created by jph on 2015/8/16.
 */
public class PayRecord implements Serializable {
    private String operate_time;//操作时间时间戳	String
    private int operate_type;//类型	Int
    private String operate_money;//	操作金额	String
    private String remark;//备注说明	String


    public String getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(String operate_time) {
        this.operate_time = operate_time;
    }

    public int getOperate_type() {
        return operate_type;
    }

    public void setOperate_type(int operate_type) {
        this.operate_type = operate_type;
    }

    public String getOperate_money() {
        return operate_money;
    }

    public void setOperate_money(String operate_money) {
        this.operate_money = operate_money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
