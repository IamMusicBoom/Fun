package com.wma.fun.auto;

import com.wma.fun.auto.luck_money.LuckMoneyWorker;

/**
 * create by wma
 * on 2020/10/9 0009
 */
public class WorkerFactory {

    public static BaseWorker createWorker(String type, AutoService service) {

        switch (type) {
            case FunctionType.RUSH_MONEY:
                return new LuckMoneyWorker(service);
//            case TypeActivity.AUTO_GET_POWER:
//                return new PowerWorker(service);
//            case TypeActivity.AUTO_GESTURE:
//                return new GestureWorker(service);
            default:
                return null;
        }
    }
}
