package com.jph.xibaibai.model.entity;

/**
 * Created by 鹏 on 2015/11/2.
 */
public class ServiceTime {
    /**服务的开始时间*/
    private String reserve_start_time;
    /**服务的结束时间*/
    private String reserve_end_time;

    public String getReserve_start_time() {
        return reserve_start_time;
    }

    public void setReserve_start_time(String reserve_start_time) {
        this.reserve_start_time = reserve_start_time;
    }

    public String getReserve_end_time() {
        return reserve_end_time;
    }

    public void setReserve_end_time(String reserve_end_time) {
        this.reserve_end_time = reserve_end_time;
    }
}
