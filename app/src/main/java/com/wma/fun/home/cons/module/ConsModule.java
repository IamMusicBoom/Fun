package com.wma.fun.home.cons.module;

import com.wma.library.base.BaseModule;
import com.wma.library.utils.http.HttpCallbackListener;
import com.wma.library.utils.http.Request;

import org.xutils.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class ConsModule extends BaseModule {
    String TAG = ConsModule.class.getSimpleName();

    String URL_CONSTELLATION = "http://web.juhe.cn:8080/constellation/getAll";

//    today,tomorrow,week,month,year

    public static final String TYPE_TODAY = "today";
    public static final String TYPE_TOMORROW = "tomorrow";
    public static final String TYPE_WEEK = "week";
    public static final String TYPE_MONTH = "month";
    public static final String TYPE_YEAR = "year";






    public void loadConstellation(HttpCallbackListener callback, String consName, String type) {
        Map<String, String> params = new HashMap<>();
        params.put("consName", consName);
        params.put("type", type);
        params.put("key", "8136c5ccf17fa37c236858e9d66ee91b");
        Request request = new Request(HttpMethod.GET, URL_CONSTELLATION,params,Request.FROM_JU_HE);
        mHttpUtils.request(request, callback);
    }
}
