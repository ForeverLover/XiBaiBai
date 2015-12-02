package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 优惠券
 * Created by jph on 2015/8/16.
 */
public class Coupon implements Serializable {
    private static final long serialVersionUID = 2990736752131388924L;

//    "id": "1",
//            "uid": "26",
//            "number": "",
//            "state": "1",
//            "time": "1234567893",
//            "coupons_price": "30",
//            "coupons_name": "洗车券",
//            "coupons_remark": "相约中秋"

    private int number;//优惠卷号码	Int
    private int state;//	状态	Int
    private long time;//使用时间时间戳	Int
    private int id;//抵用卷ID
    private int uid;
    private int type;
    private String coupons_price;//
    private String coupons_name;//
    private String coupons_remark;//
    private String server_time; // 服务器时间
    private String effective_time;// 最早可以使用时间点
    private String expired_time;// 最晚可以使用时间点

    public String getEffective_time() {
        return effective_time;
    }

    public void setEffective_time(String effective_time) {
        this.effective_time = effective_time;
    }

    public String getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(String expired_time) {
        this.expired_time = expired_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getServer_time() {
        return server_time;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCoupons_price() {
        return coupons_price;
    }

    public void setCoupons_price(String coupons_price) {
        this.coupons_price = coupons_price;
    }

    public String getCoupons_name() {
        return coupons_name;
    }

    public void setCoupons_name(String coupons_name) {
        this.coupons_name = coupons_name;
    }

    public String getCoupons_remark() {
        return coupons_remark;
    }

    public void setCoupons_remark(String coupons_remark) {
        this.coupons_remark = coupons_remark;
    }
}
