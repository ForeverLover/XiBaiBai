package com.jph.xibaibai.model.entity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 2015/11/10.
 * 订单确认页面的数据封装
 */
public class ConfirmOrder implements Serializable {
    /**用户id*/
    private String userId;
    /**车id*/
    private String carsId;
    /**车的牌号*/
    private String carNum;
    /**车的名称*/
    private String carName;
    /**车的地址*/
    private String carAddress;
    /**车的经度*/
    private String carLocateLg;
    /**车的维度*/
    private String carLocateLt;
    /**产品的id拼接*/
    private String productId;
    /**服务的总价*/
    private String allTotalPrice;
    /**预约的日期*/
    private String appointDay;
    /**预约的时刻*/
    private String appointTimeId;
    /**洗车备注说明*/
    private String reMark;
    /**洗车音频文件备注*/
    private File audioFile;
    /**优惠券id*/
    private int couponsId;
    /**缓存的产品*/
    private List<BeautyItemProduct> cachList;

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public List<BeautyItemProduct> getCachList() {
        return cachList;
    }

    public void setCachList(List<BeautyItemProduct> cachList) {
        this.cachList = cachList;
    }

    public String getAllTotalPrice() {
        return allTotalPrice;
    }

    public void setAllTotalPrice(String allTotalPrice) {
        this.allTotalPrice = allTotalPrice;
    }

    public int getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(int couponsId) {
        this.couponsId = couponsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarsId() {
        return carsId;
    }

    public void setCarsId(String carsId) {
        this.carsId = carsId;
    }

    public String getCarAddress() {
        return carAddress;
    }

    public void setCarAddress(String carAddress) {
        this.carAddress = carAddress;
    }

    public String getCarLocateLg() {
        return carLocateLg;
    }

    public void setCarLocateLg(String carLocateLg) {
        this.carLocateLg = carLocateLg;
    }

    public String getCarLocateLt() {
        return carLocateLt;
    }

    public void setCarLocateLt(String carLocateLt) {
        this.carLocateLt = carLocateLt;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAppointDay() {
        return appointDay;
    }

    public void setAppointDay(String appointDay) {
        this.appointDay = appointDay;
    }

    public String getAppointTimeId() {
        return appointTimeId;
    }

    public void setAppointTimeId(String appointTimeId) {
        this.appointTimeId = appointTimeId;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }

    public File getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(File audioFile) {
        this.audioFile = audioFile;
    }
}
