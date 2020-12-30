package com.wma.fun.auto;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.wma.library.log.Logger;

/**
 * create by wma
 * on 2020/9/15 0015
 */

public class AutoService extends AccessibilityService {
    final String TAG = AutoService.class.getSimpleName();

    BaseWorker mWorker;
    public static final String TYPE = "TYPE";

    private String mCurType;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(TAG, "onStartCommand: " + intent.getStringExtra(TYPE));
        mCurType = intent.getStringExtra(TYPE);
        mWorker = WorkerFactory.createWorker(mCurType, AutoService.this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Logger.d(TAG, "onServiceConnected: ");
        AccessibilityServiceInfo info = mWorker.getAccessibilityServiceInfo();
        setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Logger.d(TAG, "onAccessibilityEvent:  packageName = " + event.getPackageName());
//        mWorker.checkWindowElement(getRootInActiveWindow());
        mWorker.work(event);
    }





    @Override
    public void onInterrupt() {
        Logger.d(TAG, "onInterrupt: ");

    }


}
