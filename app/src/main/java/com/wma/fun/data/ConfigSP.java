package com.wma.fun.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.wma.fun.MainApplication;
import com.wma.library.utils.ConsUtils;

/**
 * create by wma
 * on 2020/11/23 0023
 */
public class ConfigSP {

    static final String CONFIG_SP_NAME = "config_sp";

    private static Context mContext;
    private static SharedPreferences sp;

    static {
        mContext = MainApplication.getContext();
        sp = mContext.getSharedPreferences(CONFIG_SP_NAME, Context.MODE_PRIVATE);
    }

    // ----------------------------------------------------------------------- 是否为第一次使用 start
    static final String FIRST_USE_KEY = "FIRST_USE_KEY";

    public static void putFirstUse(boolean isFirst) {
        sp.edit().putBoolean(FIRST_USE_KEY, isFirst).apply();
    }

    public static boolean getFirstUse() {
        return sp.getBoolean(FIRST_USE_KEY, true);
    }


    // ----------------------------------------------------------------------- 是否为第一次使用 end
}
