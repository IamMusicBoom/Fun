package com.wma.fun.home.weather.module;

/**
 * create by wma
 * on 2020/10/30 0030
 */
public class WidsModule extends WeatherNetModule {


    /**
     * wid : 04
     * weather : 雷阵雨
     */

    private String wid;
    private String weather;
    private int weatherImg;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getWeatherImg() {
        return weatherImg;
    }

    public void setWeatherImg(int weatherImg) {
        this.weatherImg = weatherImg;
    }
}
