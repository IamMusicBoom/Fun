package com.wma.fun.home.news;

import android.graphics.RectF;

import com.wma.library.base.BaseModule;

/**
 * create by wma
 * on 2020/11/5 0005
 */
@Deprecated
public class KindModule extends BaseModule {
    private RectF rect;
    private String key;
    private String value;

    public KindModule(RectF rect, String key, String value) {
        this.rect = rect;
        this.key = key;
        this.value = value;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
