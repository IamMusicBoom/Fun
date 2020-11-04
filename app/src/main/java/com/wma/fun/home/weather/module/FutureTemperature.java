package com.wma.fun.home.weather.module;

import android.graphics.PointF;

import com.wma.fun.home.weather.Calibration;

/**
 * create by wma
 * on 2020/11/3 0003
 */
public class FutureTemperature {
    private String maxTemperature;
    private String minTemperature;

    private PointF maxPoint;
    private PointF minPoint;




    private Calibration calibration;

    private String date;

    public FutureTemperature(String maxTemperature, String minTemperature, String date) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.date = date;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public PointF getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(PointF maxPoint) {
        this.maxPoint = maxPoint;
    }

    public PointF getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(PointF minPoint) {
        this.minPoint = minPoint;
    }

    public Calibration getCalibration() {
        return calibration;
    }

    public void setCalibration(Calibration calibration) {
        this.calibration = calibration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
