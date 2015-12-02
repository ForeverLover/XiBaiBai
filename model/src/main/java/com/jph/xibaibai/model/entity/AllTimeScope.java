package com.jph.xibaibai.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jph on 2015/9/20.
 */
public class AllTimeScope implements Serializable{

    private static final long serialVersionUID = 5286365031977417993L;
    private List<TimeScope> one;
    private List<TimeScope> two;
    private List<TimeScope> three;
    private String currenttime;

    public List<TimeScope> getOne() {
        return one;
    }

    public void setOne(List<TimeScope> one) {
        this.one = one;
    }

    public List<TimeScope> getTwo() {
        return two;
    }

    public void setTwo(List<TimeScope> two) {
        this.two = two;
    }

    public List<TimeScope> getThree() {
        return three;
    }

    public void setThree(List<TimeScope> three) {
        this.three = three;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }
}
