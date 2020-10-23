package com.wma.fun.main;

import android.os.Binder;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class MainBinder extends Binder {

    MainService mService;
    public MainBinder(MainService service) {
        this.mService = service;
    }

    public MainService getService(){
        return mService;
    }

}
