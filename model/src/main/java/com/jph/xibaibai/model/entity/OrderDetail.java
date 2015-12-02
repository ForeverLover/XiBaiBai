package com.jph.xibaibai.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情
 * Created by jph on 2015/9/14.
 */
public class OrderDetail extends Order implements Serializable {
    private static final long serialVersionUID = -6035087442474998832L;

    private int pay_type;//支付类型	Int	是 0支付宝
    private long pay_time;//支付时间时间戳	Int	是
    private double pay_num;//支付金额	double	是
    private String emp_advice;//员工建议产品id	String	是
    private String emp_advice_imgs;//建议图片	String	是
    private String emp_reminder;//员工温馨提示	String	是
    private String car_wash_before_img;//	洗前照片	String	是 最多5张，“,”分隔
    private String car_wash_end_img;//洗后照片	String	是 最多5张，“,”分隔
    private List<OrderSuggestion> list_ad;//建议
    private List<OrderReminder> list_pr;//温馨提示

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public long getPay_time() {
        return pay_time;
    }

    public void setPay_time(long pay_time) {
        this.pay_time = pay_time;
    }

    public double getPay_num() {
        return pay_num;
    }

    public void setPay_num(double pay_num) {
        this.pay_num = pay_num;
    }

    public String getEmp_advice() {
        return emp_advice;
    }

    public void setEmp_advice(String emp_advice) {
        this.emp_advice = emp_advice;
    }

    public String getEmp_advice_imgs() {
        return emp_advice_imgs;
    }

    public void setEmp_advice_imgs(String emp_advice_imgs) {
        this.emp_advice_imgs = emp_advice_imgs;
    }

    public String getEmp_reminder() {
        return emp_reminder;
    }

    public void setEmp_reminder(String emp_reminder) {
        this.emp_reminder = emp_reminder;
    }

    public String getCar_wash_before_img() {
        return car_wash_before_img;
    }

    public void setCar_wash_before_img(String car_wash_before_img) {
        this.car_wash_before_img = car_wash_before_img;
    }

    public String getCar_wash_end_img() {
        return car_wash_end_img;
    }

    public void setCar_wash_end_img(String car_wash_end_img) {
        this.car_wash_end_img = car_wash_end_img;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<OrderSuggestion> getList_ad() {
        return list_ad;
    }

    public void setList_ad(List<OrderSuggestion> list_ad) {
        this.list_ad = list_ad;
    }

    public List<OrderReminder> getList_pr() {
        return list_pr;
    }

    public void setList_pr(List<OrderReminder> list_pr) {
        this.list_pr = list_pr;
    }
}
