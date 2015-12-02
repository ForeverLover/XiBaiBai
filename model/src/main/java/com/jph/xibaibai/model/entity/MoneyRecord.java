package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 资金记录
 * Created by jph on 2015/9/4.
 */
public class MoneyRecord implements Serializable {
    private static final long serialVersionUID = -4348822722868415909L;

    private long operate_time;
    private int operate_type;//类型
    private double operate_money;//操作金额
    private String remark;

    public long getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(long operate_time) {
        this.operate_time = operate_time;
    }

    public int getOperate_type() {
        return operate_type;
    }

    public void setOperate_type(int operate_type) {
        this.operate_type = operate_type;
    }

    public double getOperate_money() {
        return operate_money;
    }

    public void setOperate_money(double operate_money) {
        this.operate_money = operate_money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
