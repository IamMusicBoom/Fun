package com.wma.fun.home.weather.module;

import com.wma.library.base.BaseModule;
import com.wma.library.utils.http.HttpCallbackListener;
import com.wma.library.utils.http.Request;

import org.xutils.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * create by wma
 * on 2020/10/30 0030
 */
public class WeatherNetModule extends BaseModule {

    String URL_WIDS = "http://apis.juhe.cn/simpleWeather/wids";

    String URL_WEATHER = "http://apis.juhe.cn/simpleWeather/query";

    public void loadWids(HttpCallbackListener callback) {
        Map<String, String> params = new HashMap<>();
        params.put("key", "795896001e17442acb2ca48ae1ea3167");
        Request request = new Request(HttpMethod.GET, URL_WIDS, params, Request.FROM_JU_HE);
        mHttpUtils.request(request, callback);
    }


    public void loadWeather(HttpCallbackListener callback, String city) {
        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        params.put("key", "795896001e17442acb2ca48ae1ea3167");
        Request request = new Request(HttpMethod.GET, URL_WEATHER, params, Request.FROM_JU_HE);
        mHttpUtils.request(request, callback);
    }
}
