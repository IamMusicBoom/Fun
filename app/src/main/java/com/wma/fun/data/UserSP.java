package com.wma.fun.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.wma.fun.MainApplication;
import com.wma.library.utils.ConsUtils;

/**
 * create by wma
 * on 2020/10/29 0029
 */
public class UserSP {

    static final String USER_SP_NAME = "user_sp";

    private static Context mContext;
    private static SharedPreferences sp;

    static {
        mContext = MainApplication.getContext();
        sp = mContext.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE);
    }

    // ----------------------------------------------------------------------- 用户星座 start
    static final String USER_CONS_KEY = "USER_CONS_KEY";

    public static void putUserCons(String cons) {
        sp.edit().putString(USER_CONS_KEY, cons).apply();
    }

    public static String getUserCons() {
        return sp.getString(USER_CONS_KEY, ConsUtils.J_X_Z);
    }
    // ----------------------------------------------------------------------- 用户星座 end

}
