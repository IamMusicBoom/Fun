package com.wma.fun.main;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.wma.library.utils.NotificationUtils;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class MainService extends Service {
    NotificationUtils mNotificationUtil;

    @Override
    public void onCreate() {
        super.onCreate();

        mNotificationUtil = new NotificationUtils(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1001, mNotificationUtil.createDefaultBuilder().build());
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MainBinder(this);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
