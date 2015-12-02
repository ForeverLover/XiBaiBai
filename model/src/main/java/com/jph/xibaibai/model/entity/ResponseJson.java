package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * Created by jph on 2015/8/12.
 */
public class ResponseJson implements Serializable{

    private int code;//结果码1为成功
    private String msg;//提示信息
    private Object result;//返回的数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
