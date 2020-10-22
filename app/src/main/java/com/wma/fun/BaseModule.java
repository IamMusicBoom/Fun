package com.wma.fun;

import org.json.JSONObject;

/**
 * create by wma
 * on 2020/10/14 0014
 */
public class BaseModule {
    private String msg;
    private int code;
    private JSONObject data;

    public String getMsg() {
        return msg;

    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
