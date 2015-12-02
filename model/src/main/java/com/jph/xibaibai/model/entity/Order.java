package com.jph.xibaibai.model.entity;

import java.io.File;
import java.io.Serializable;

/**
 * Created by jph on 2015/8/16.
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 6956074128283466848L;
//    uid	用户ID	Int	否
//    id	订单id	Int	否
//    location	车辆位置	String	否
//    location_lg	车辆位置经度	String	否
//    location_lt	车辆位置纬度	String	否
//    remark	备注	String	否
//    p_ids	产品ID	Int	否	可选择多个产品，id号以“,”分隔
//    total_price	总价	String	否
//    order_reg_id	员工id	Int	否
//    plan_time	预计完成时间	String	否
//    order_num	订单号	Int	否
//    order_name	订单标题	String	否
//    order_state	订单状态	Int	否	状态0未付款1派单中2已派单3在路上4进行中5未评价6已评价7已取消
//    emp_iphone	接单员工手机号	String	是
//    emp_name	接单员工名字	String	是
//    emp_img	接单员工头像	String	是
//    Star	评价星级	String	是

    private int uid;//用户ID	Int	否
    private int id;//订单id	Int	否
    private String location;//车辆位置	String	否
    private String location_lg;//车辆位置经度	String	否
    private String location_lt;//车辆位置纬度	String	否
    private String remark;//备注	String	否
    private String p_ids;//产品ID	Int	否
    private double total_price;//	总价	String	否
    private int order_reg_id;//员工id	Int	否
    private String plan_time;//预计完成时间	String	否
    private String order_num;//	订单号	Int	否
    private String order_name;//	订单标题	String	否
    private int order_state;//	订单状态Int	否	状态0未付款1派单中2已派单3在路上4进行中5未评价6已评价7已取消
    private String emp_iphone;//	接单员工手机号	String	是
    private String emp_name;//	接单员工名字	String	是
    private String emp_img;//	接单员工头像	String	是
    private float star;//	评价星级	String	是
    private String c_ids;//车辆id
    private int p_order_time_cid;//下单时间段id
    private long p_order_time;//下单时间
    private String c_plate_num;//车牌
    private String c_name;//车类型
    private String c_brand;//品牌
    private String c_color;//车辆颜色
    private int c_Type; // 车的类型
    private String current_address;
    private String current_address_lg;
    private String current_address_lt;
    private long day;//预约当前时间戳

    ////////////////////本地使用////////////

    public int getC_Type() {
        return c_Type;
    }

    public void setC_Type(int c_Type) {
        this.c_Type = c_Type;
    }

    private File fileVoice;//录音

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation_lg() {
        return location_lg;
    }

    public void setLocation_lg(String location_lg) {
        this.location_lg = location_lg;
    }

    public String getLocation_lt() {
        return location_lt;
    }

    public void setLocation_lt(String location_lt) {
        this.location_lt = location_lt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getP_ids() {
        return p_ids;
    }

    public void setP_ids(String p_ids) {
        this.p_ids = p_ids;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getOrder_reg_id() {
        return order_reg_id;
    }

    public void setOrder_reg_id(int order_reg_id) {
        this.order_reg_id = order_reg_id;
    }

    public String getPlan_time() {
        return plan_time;
    }

    public void setPlan_time(String plan_time) {
        this.plan_time = plan_time;
    }


    public File getFileVoice() {
        return fileVoice;
    }

    public void setFileVoice(File fileVoice) {
        this.fileVoice = fileVoice;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getEmp_iphone() {
        return emp_iphone;
    }

    public void setEmp_iphone(String emp_iphone) {
        this.emp_iphone = emp_iphone;
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

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getC_ids() {
        return c_ids;
    }

    public void setC_ids(String c_ids) {
        this.c_ids = c_ids;
    }

    public int getP_order_time_cid() {
        return p_order_time_cid;
    }

    public void setP_order_time_cid(int p_order_time_cid) {
        this.p_order_time_cid = p_order_time_cid;
    }

    public long getP_order_time() {
        return p_order_time;
    }

    public void setP_order_time(long p_order_time) {
        this.p_order_time = p_order_time;
    }

    public String getC_plate_num() {
        return c_plate_num;
    }

    public void setC_plate_num(String c_plate_num) {
        this.c_plate_num = c_plate_num;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_color() {
        return c_color;
    }

    public void setC_color(String c_color) {
        this.c_color = c_color;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public String getCurrent_address_lg() {
        return current_address_lg;
    }

    public void setCurrent_address_lg(String current_address_lg) {
        this.current_address_lg = current_address_lg;
    }

    public String getCurrent_address_lt() {
        return current_address_lt;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public String getC_brand() {
        return c_brand;
    }

    public void setC_brand(String c_brand) {
        this.c_brand = c_brand;
    }

    public void setCurrent_address_lt(String current_address_lt) {
        this.current_address_lt = current_address_lt;
    }
}
