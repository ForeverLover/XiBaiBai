package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 用户车辆
 * Created by jph on 2015/8/16.
 */
public class Car implements Serializable {
//    id	车辆ID	int
//    uid	用户ID	int
//    c_img	车辆图片	String
//    c_plate_num	车牌号	String
//c_type	车辆类型	Int	否	1微型2小型3紧凑型4中型5中大型6大型7suv8mpv9跑车10皮卡11微面12电动车
//    c_color	车辆颜色	String	否
//    add_time	添加时间	Int	否
//    c_remark	备注	String	是


    private int id;
    private int uid;
    private String c_img;
    private String c_plate_num;
    private int c_type;
    private String c_color;//
    private long add_time;
    private String c_remark;
    private String c_brand;

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

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public String getC_plate_num() {
        return c_plate_num;
    }

    public void setC_plate_num(String c_plate_num) {
        this.c_plate_num = c_plate_num;
    }

    public int getC_type() {
        return c_type;
    }

    public void setC_type(int c_type) {
        this.c_type = c_type;
    }

    public String getC_color() {
        return c_color;
    }

    public void setC_color(String c_color) {
        this.c_color = c_color;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getC_remark() {
        return c_remark;
    }

    public void setC_remark(String c_remark) {
        this.c_remark = c_remark;
    }

    public String getC_brand() {
        return c_brand;
    }

    public void setC_brand(String c_brand) {
        this.c_brand = c_brand;
    }
}
