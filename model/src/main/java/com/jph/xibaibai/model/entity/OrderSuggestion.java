package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 订单建议
 * Created by jph on 2015/9/20.
 */
public class OrderSuggestion implements Serializable{

    private static final long serialVersionUID = 8508263294418100853L;
    private int id;
    private int order_id;
    private String ad_img;
    private String ad_title;
    private String ad_content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getAd_img() {
        return ad_img;
    }

    public void setAd_img(String ad_img) {
        this.ad_img = ad_img;
    }

    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public String getAd_content() {
        return ad_content;
    }

    public void setAd_content(String ad_content) {
        this.ad_content = ad_content;
    }
}
