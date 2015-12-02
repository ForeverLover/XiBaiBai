package com.jph.xibaibai.model.entity;

import java.util.List;

/**
 * Created by Eric on 2015/11/11.
 * 确认订单页面产品列表Modle类
 */
public class ConfirmOrderData {
    /**订单的ID*/
    private String oderId;
    /**订单号码*/
    private String orderNum;
    /**订单时间*/
    private String orderTime;
    /**订单总价*/
    private String orderPrice;
    /**订单产品列表*/
    private List<BeautyItemProduct> productList;

    public String getOderId() {
        return oderId;
    }

    public void setOderId(String oderId) {
        this.oderId = oderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public List<BeautyItemProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<BeautyItemProduct> productList) {
        this.productList = productList;
    }
}
