package com.wma.fun.home.weather.module;

import com.wma.library.base.BaseModule;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * create by wma
 * on 2020/10/30 0030
 */
public class WeatherNetModule extends BaseModule {

    String WIDS_URL = "http://apis.juhe.cn/simpleWeather/wids";

    String WEATHER_URL = "http://apis.juhe.cn/simpleWeather/query";

    public void loadWids(Callback.CommonCallback callback){
        Map<String, String> params = new HashMap<>();
        params.put("key", "795896001e17442acb2ca48ae1ea3167");
        mCancelable = mHttpUtils.get(WIDS_URL, params, callback);
    }


    public void loadWeather(Callback.CommonCallback callback, String city){
        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        params.put("key", "795896001e17442acb2ca48ae1ea3167");
        mCancelable = mHttpUtils.get(WEATHER_URL, params, callback);
    }
}
