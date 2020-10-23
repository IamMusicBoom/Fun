package com.wma.fun.home;

import com.wma.fun.BaseModule;
import com.wma.library.log.Logger;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class HomeModule extends BaseModule {
    String TAG = HomeModule.class.getSimpleName();

    String CONSTELLATION_URL = "http://web.juhe.cn:8080/constellation/getAll";

    public void loadConstellation(Callback.CommonCallback callback){
        Map<String,String> params = new HashMap<>();
        params.put("consName","狮子座");
        params.put("type","today");
        params.put("key","8136c5ccf17fa37c236858e9d66ee91b");
        mCancelable = mHttpUtils.get(CONSTELLATION_URL, params, callback);
    }
}
