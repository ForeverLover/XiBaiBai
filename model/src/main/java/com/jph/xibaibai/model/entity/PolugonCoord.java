package com.jph.xibaibai.model.entity;

/**
 * Created by 鹏 on 2015/11/6.
 * 圆和多边形的经纬度子Model类
 */
public class PolugonCoord {

    private String id;

    private String server_lng;

    private String server_lat;

    private String type;

    private String radius;

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServer_lng() {
        return server_lng;
    }

    public void setServer_lng(String server_lng) {
        this.server_lng = server_lng;
    }

    public String getServer_lat() {
        return server_lat;
    }

    public void setServer_lat(String server_lat) {
        this.server_lat = server_lat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
