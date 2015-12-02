package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 系统消息
 * Created by jph on 2015/9/20.
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -7048192969617191654L;
    private int uid;
    private int id;
    private String a_m_tit;
    private long a_m_time;
    private String a_m_con;
    private int a_m_type;

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

    public String getA_m_tit() {
        return a_m_tit;
    }

    public void setA_m_tit(String a_m_tit) {
        this.a_m_tit = a_m_tit;
    }

    public long getA_m_time() {
        return a_m_time;
    }

    public void setA_m_time(long a_m_time) {
        this.a_m_time = a_m_time;
    }

    public String getA_m_con() {
        return a_m_con;
    }

    public void setA_m_con(String a_m_con) {
        this.a_m_con = a_m_con;
    }

    public int getA_m_type() {
        return a_m_type;
    }

    public void setA_m_type(int a_m_type) {
        this.a_m_type = a_m_type;
    }
}
