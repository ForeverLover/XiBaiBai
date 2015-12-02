package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 用户信息
 * Created by jph on 2015/8/16.
 */
public class UserInfo implements Serializable {
//    iphone	手机号	int	否
//    uname	昵称	String	是
//    weixin	微信	int	是
//    qq	Qq	Inr	是
//    email	邮箱	String	是
//    age	年龄	Int	是
//    profession	职业	String	是
//    sex	性别	Int	是	1-男2-女

    private String iphone;
    private String uname;
    private String weixin;
    private String qq;
    private String email;
    private int age;
    private String profession;
    private int sex;
    private String u_img;

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getU_img() {
        return u_img;
    }

    public void setU_img(String u_img) {
        this.u_img = u_img;
    }
}
