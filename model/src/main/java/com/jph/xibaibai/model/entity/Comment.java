package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * Created by jph on 2015/9/13.
 */
public class Comment implements Serializable {
//    order_id	订单ID	Int	否
//    comment	评论内容	String	否
//    star	星级	Float	否
//    Order_id	订单id	Int	否
//    comment_time	评论时间	Int	否
//    order_num	订单号	Int	否
//    emp_name	接单员工名	String	是
//    emp_img	接单员工头像	String	是
//    emp_iphone	接单员工电话	String	是
//    c_plate_num	车牌	String	是
//    c_car	车系列	String	是

    private int order_id;
    private String comment;
    private float star;
    private long comment_time;//评论时间
    private String order_num;
    private String emp_name;//
    private String emp_img;//
    private String emp_iphone;//
    private String c_plate_num;//
    private String c_car;//

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public long getComment_time() {
        return comment_time;
    }

    public void setComment_time(long comment_time) {
        this.comment_time = comment_time;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_img() {
        return emp_img;
    }

    public void setEmp_img(String emp_img) {
        this.emp_img = emp_img;
    }

    public String getEmp_iphone() {
        return emp_iphone;
    }

    public void setEmp_iphone(String emp_iphone) {
        this.emp_iphone = emp_iphone;
    }

    public String getC_plate_num() {
        return c_plate_num;
    }

    public void setC_plate_num(String c_plate_num) {
        this.c_plate_num = c_plate_num;
    }

    public String getC_car() {
        return c_car;
    }

    public void setC_car(String c_car) {
        this.c_car = c_car;
    }
}
