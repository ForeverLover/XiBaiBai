package com.jph.xibaibai.model.entity;

/**
 * Created by jph on 2015/8/16.
 */
public class CouponRecord {

    private int order_id;//优惠卷号码	Int
    private int time;//使用时间时间戳	Int

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
