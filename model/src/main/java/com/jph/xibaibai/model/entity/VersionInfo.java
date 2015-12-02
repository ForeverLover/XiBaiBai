package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 版本信息
 * Created by jph on 2015/9/29.
 */
public class VersionInfo implements Serializable {

    private static final long serialVersionUID = 360796804747203938L;
    private int id;
    private int versionCode;
    private String versionName;
    private long update_time;
    private String update_content;
    private String downloadAddress;
    private int version_type;//1android,2ios

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_content() {
        return update_content;
    }

    public void setUpdate_content(String update_content) {
        this.update_content = update_content;
    }

    public String getDownloadAddress() {
        return downloadAddress;
    }

    public void setDownloadAddress(String downloadAddress) {
        this.downloadAddress = downloadAddress;
    }

    public int getVersion_type() {
        return version_type;
    }

    public void setVersion_type(int version_type) {
        this.version_type = version_type;
    }
}
