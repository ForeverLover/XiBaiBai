package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * diy产品
 * Created by jph on 2015/9/14.
 */
public class DIYProduct extends Product implements Serializable {
    private static final long serialVersionUID = -2272363618675557517L;
    private int p_type;//支付类型	Int	否	0线下支付（人工收费）1线上支付（继续连接支付宝）
    private String p_wimg;//	未中图片 String	否	http://s-199705.gotocdn.com/ /Public/images/diy/图片名
    private String p_ximg;//	选中图片	String	否	http://s-199705.gotocdn.com/ /Public/images/diy/图片名
    private int p_type_t;//0 为基础服务，5为DIY服务

    public int getP_type() {
        return p_type;
    }

    public void setP_type(int p_type) {
        this.p_type = p_type;
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

    public int getP_type_t() {
        return p_type_t;
    }

    public void setP_type_t(int p_type_t) {
        this.p_type_t = p_type_t;
    }
}
