package com.wma.fun.home.weather.module;

import com.wma.library.base.BaseModule;

import java.util.List;

/**
 * create by wma
 * on 2020/10/30 0030
 */
public class WeatherModule extends WeatherNetModule {



    /**
     * city : 成都
     * realtime : {"temperature":"17","humidity":"76","info":"多云","wid":"01","direct":"东北风","power":"2级","aqi":"69"}
     * future : [{"date":"2020-10-30","temperature":"13/18℃","weather":"小雨","wid":{"day":"07","night":"07"},"direct":"持续无风向"},{"date":"2020-10-31","temperature":"13/19℃","weather":"阴","wid":{"day":"02","night":"02"},"direct":"持续无风向"},{"date":"2020-11-01","temperature":"13/21℃","weather":"阴","wid":{"day":"02","night":"02"},"direct":"持续无风向"},{"date":"2020-11-02","temperature":"12/22℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"持续无风向"},{"date":"2020-11-03","temperature":"11/16℃","weather":"多云转小雨","wid":{"day":"01","night":"07"},"direct":"持续无风向"}]
     */

    private String city;
    private RealtimeBean realtime;
    private List<FutureBean> future;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public RealtimeBean getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeBean realtime) {
        this.realtime = realtime;
    }

    public List<FutureBean> getFuture() {
        return future;
    }

    public void setFuture(List<FutureBean> future) {
        this.future = future;
    }

    public static class RealtimeBean {
        /**
         * temperature : 17
         * humidity : 76
         * info : 多云
         * wid : 01
         * direct : 东北风
         * power : 2级
         * aqi : 69
         */

        private String temperature;
        private String humidity;
        private String info;
        private String wid;
        private String direct;
        private String power;
        private String aqi;

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        public String getDirect() {
            return direct;
        }

        public void setDirect(String direct) {
            this.direct = direct;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }
    }

    public static class FutureBean {
        /**
         * date : 2020-10-30
         * temperature : 13/18℃
         * weather : 小雨
         * wid : {"day":"07","night":"07"}
         * direct : 持续无风向
         */

        private String date;
        private String temperature;
        private String weather;
        private WidBean wid;
        private String direct;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public WidBean getWid() {
            return wid;
        }

        public void setWid(WidBean wid) {
            this.wid = wid;
        }

        public String getDirect() {
            return direct;
        }

        public void setDirect(String direct) {
            this.direct = direct;
        }

        public static class WidBean {
            /**
             * day : 07
             * night : 07
             */

            private String day;
            private String night;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getNight() {
                return night;
            }

            public void setNight(String night) {
                this.night = night;
            }
        }
    }

//    795896001e17442acb2ca48ae1ea3167






}
