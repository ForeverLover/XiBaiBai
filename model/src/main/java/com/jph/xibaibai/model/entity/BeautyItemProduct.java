package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * Created by Eric on 2015/11/9.
 * DIY子项的Model类，打蜡子项，非必须清洗车的服务
 */
public class BeautyItemProduct implements Serializable{
    /**此DIY子项的id*/
    private String id;
    /**此DIY子项的名称*/
    private String p_name;
    /**此DIY子项的价格（轿车）*/
    private String p_price;
    /**此DIY子项的价格（SUV）*/
    private String p_price2;
    /**此DIY子项的简介*/
    private String p_info;
    /**此DIY子项的详细信息*/
    private String p_info_detail;
    private String p_type;
    private String p_type_t;
    private String p_cuo;
    private String p_time;
    /**此DIY子项的未选中的图片*/
    private String p_wimg;
    /**此DIY子项的选中的图片*/
    private String p_ximg;

    private String is_show;

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getP_price2() {
        return p_price2;
    }

    public void setP_price2(String p_price2) {
        this.p_price2 = p_price2;
    }

    public String getP_info() {
        return p_info;
    }

    public void setP_info(String p_info) {
        this.p_info = p_info;
    }

    public String getP_info_detail() {
        return p_info_detail;
    }

    public void setP_info_detail(String p_info_detail) {
        this.p_info_detail = p_info_detail;
    }

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    public String getP_type_t() {
        return p_type_t;
    }

    public void setP_type_t(String p_type_t) {
        this.p_type_t = p_type_t;
    }

    public String getP_cuo() {
        return p_cuo;
    }

    public void setP_cuo(String p_cuo) {
        this.p_cuo = p_cuo;
    }

    public String getP_time() {
        return p_time;
    }

    public void setP_time(String p_time) {
        this.p_time = p_time;
    }

    public String getP_wimg() {
        return p_wimg;
    }

    public void setP_wimg(String p_wimg) {
        this.p_wimg = p_wimg;
    }

    public String getP_ximg() {
        return p_ximg;
    }

    public void setP_ximg(String p_ximg) {
        this.p_ximg = p_ximg;
    }
}
