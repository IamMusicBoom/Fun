package com.wma.fun;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wma.library.base.BaseApplication;
import com.wma.library.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/10/14 0014
 */
public class MainApplication extends BaseApplication implements Application.ActivityLifecycleCallbacks {
    private final String TAG = MainApplication.class.getSimpleName();
    private final List<Activity> mActivities = new ArrayList<>();

    public static MainApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        registerActivityLifecycleCallbacks(this);

    }

    public static MainApplication getApplication(){
        return mContext;
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onActivityCreated: name = " + activity.getClass().getSimpleName());
        mActivities.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Logger.d(TAG, "onActivityDestroyed: name = " + activity.getClass().getSimpleName());
        mActivities.remove(activity);
    }


    /**
     * 退出登录
     */
    public void exit() {
        for (int i = 0; i < this.mActivities.size(); i++) {
            Activity activity = mActivities.get(i);
            Logger.d(TAG, "exit: name = " + activity.getClass().getSimpleName());
            activity.finish();
        }
    }
}
