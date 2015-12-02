package com.jph.xibaibai.model.entity;

/**
 * Created by Eric on 2015/11/10.
 * 优惠券Model类
 */
public class MyCoupons {
    /**优惠券id*/
    private String coupons_id;
    /**优惠券抵用价格*/
    private String coupons_price;
    /**洗车券的名字*/
    private String coupons_name;
    /**优惠券内容*/
    private String coupons_remark;
    /**优惠券过期时间*/
    private String expired_time;

    public String getCoupons_name() {
        return coupons_name;
    }

    public void setCoupons_name(String coupons_name) {
        this.coupons_name = coupons_name;
    }

    public String getCoupons_id() {
        return coupons_id;
    }

    public void setCoupons_id(String coupons_id) {
        this.coupons_id = coupons_id;
    }

    public String getCoupons_price() {
        return coupons_price;
    }

    public void setCoupons_price(String coupons_price) {
        this.coupons_price = coupons_price;
    }

    public String getCoupons_remark() {
        return coupons_remark;
    }

    public void setCoupons_remark(String coupons_remark) {
        this.coupons_remark = coupons_remark;
    }

    public String getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(String expired_time) {
        this.expired_time = expired_time;
    }
}
