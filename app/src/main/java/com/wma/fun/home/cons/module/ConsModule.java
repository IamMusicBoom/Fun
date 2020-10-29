package com.wma.fun.home.cons.module;

import com.wma.library.base.BaseModule;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class ConsModule extends BaseModule {
    String TAG = ConsModule.class.getSimpleName();

    String CONSTELLATION_URL = "http://web.juhe.cn:8080/constellation/getAll";

//    today,tomorrow,week,month,year

    public static final String TYPE_TODAY = "today";
    public static final String TYPE_TOMORROW = "tomorrow";
    public static final String TYPE_WEEK = "week";
    public static final String TYPE_MONTH = "month";
    public static final String TYPE_YEAR = "year";






    public void loadConstellation(Callback.CommonCallback callback, String consName, String type) {
        Map<String, String> params = new HashMap<>();
        params.put("consName", consName);
        params.put("type", type);
        params.put("key", "8136c5ccf17fa37c236858e9d66ee91b");
        mCancelable = mHttpUtils.get(CONSTELLATION_URL, params, callback);
    }
}
