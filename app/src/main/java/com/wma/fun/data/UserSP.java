package com.wma.fun.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.wma.fun.MainApplication;
import com.wma.fun.login.module.User;
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
        String curConstellation = ConsUtils.getCurConstellation();
        return sp.getString(USER_CONS_KEY, curConstellation);
    }


    // ----------------------------------------------------------------------- 用户星座 end


    // ----------------------------------------------------------------------- 用户对新闻的喜好 start
    static final String USER_FAVORITE_KEY = "USER_FAVORITE_KEY";

    public static void putFavorite(String favorite) {
        sp.edit().putString(USER_FAVORITE_KEY, favorite).apply();
    }

    public static String getFavorite() {
        return sp.getString(USER_FAVORITE_KEY, "top");
    }
    // ----------------------------------------------------------------------- 用户对新闻的喜好 end


    // ----------------------------------------------------------------------- 用户Token start
    public static String USER_TOKEN_KEY = "USER_TOKEN_KEY";

    public static void putToken(String token) {
        sp.edit().putString(USER_TOKEN_KEY, token).apply();
    }

    public static String getToken() {
        return sp.getString(USER_TOKEN_KEY, "");
    }
    // ----------------------------------------------------------------------- 用户Token end


    // ----------------------------------------------------------------------- 用户信息 start
    public static String USER_KEY = "USER_KEY";

    public static void putUser(User user) {
        String userStr = new Gson().toJson(user);
        sp.edit().putString(USER_KEY, userStr).apply();
    }

    public static User getUser() {
        String userStr = sp.getString(USER_KEY, "");
        User user = new Gson().fromJson(userStr, User.class);
        return user;
    }
    // ----------------------------------------------------------------------- 用户信息 end

    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }

}
