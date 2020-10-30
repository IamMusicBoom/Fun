package com.wma.fun.home.weather;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * create by wma
 * on 2020/10/29 0029
 * 刻度对象
 */
public class Calibration {


    private PointF startP;
    private PointF endP;
    private int strokeWidth;
    private int number;

    private boolean isMax;
    private boolean isMin;
    private boolean isCur;
    private String numberStr;

    public Calibration(PointF startP, PointF endP, int strokeWidth, int number) {
        this.startP = startP;
        this.endP = endP;
        this.strokeWidth = strokeWidth;
        this.number = number;
        this.numberStr = String.valueOf(number);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public PointF getStartP() {
        return startP;
    }

    public void setStartP(PointF startP) {
        this.startP = startP;
    }

    public PointF getEndP() {
        return endP;
    }

    public void setEndP(PointF endP) {
        this.endP = endP;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public boolean isMax() {
        return isMax;
    }

    public void setMax(boolean max) {
        isMax = max;
    }

    public boolean isMin() {
        return isMin;
    }

    public void setMin(boolean min) {
        isMin = min;
    }

    public boolean isCur() {
        return isCur;
    }

    public void setCur(boolean cur) {
        isCur = cur;
    }

    public String getNumberStr() {
        return numberStr;
    }

    public void setNumberStr(String numberStr) {
        this.numberStr = numberStr;
    }
}
