package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 温馨提示
 * Created by jph on 2015/9/20.
 */
public class OrderReminder implements Serializable {
    private static final long serialVersionUID = 1271833320570793231L;

    private int id;
    private int order_id;
    private String pr_img;
    private String pr_content;

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

    public String getPr_img() {
        return pr_img;
    }

    public void setPr_img(String pr_img) {
        this.pr_img = pr_img;
    }

    public String getPr_content() {
        return pr_content;
    }

    public void setPr_content(String pr_content) {
        this.pr_content = pr_content;
    }
}
