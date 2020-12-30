package com.wma.fun.auto.luck_money;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;


import com.wma.fun.auto.BaseWorker;
import com.wma.library.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/10/9 0009
 */
public class LuckMoneyWorker extends BaseWorker {
    final String TAG = LuckMoneyWorker.class.getSimpleName();

    public LuckMoneyWorker(AccessibilityService service) {
        super(service);
    }

    @Override
    public void work(AccessibilityEvent event) {
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏变化
                if (event.getPackageName().toString().contains("systemui")) {
                    findViewAndClick(getRootInActiveWindow(), "微信红包");
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:// 窗口内容发生变化
                if (event.getPackageName().toString().contains("tencent")) {
                    goRushLuckMoney(event);
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:// 窗口内容发生变化
                if (event.getPackageName().toString().contains("systemui")) {
                    findViewAndClick(getRootInActiveWindow(), "微信红包");
                } else if (event.getPackageName().toString().contains("tencent")) {
                    goRushLuckMoney(event);
                }
                break;
                case AccessibilityEvent.TYPE_VIEW_SELECTED:
                    AccessibilityNodeInfo openBtn = findOpenBtn();
                    if (openBtn != null) {
                        goClick(openBtn);
                    }
                    break;
        }
    }

    @Override
    public AccessibilityServiceInfo getAccessibilityServiceInfo() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        // 事件类型
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;

        //过滤的包名
        String[] packageNames = {"com.tencent.mm","com.android.systemui"};
        info.packageNames = packageNames;
        return info;
    }

    private void goRushLuckMoney(AccessibilityEvent event) {
        List<AccessibilityNodeInfo> needClickBtns = findNeedClickBtns();
        if (needClickBtns != null) {
            if (needClickBtns.size() > 0) {
                goClick(needClickBtns.get(0));
            }
        }
        AccessibilityNodeInfo openBtn = findOpenBtn();
        if (openBtn != null) {
            goClick(openBtn);
        }else{

        }
        if(event.getClassName().toString().contains("LuckyMoneyDetailUI")){
            mService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);

        }
    }


    /**
     * 找到按钮并且点击
     *
     * @param info
     * @param text
     */
    private void findViewAndClick(AccessibilityNodeInfo info, String text) {
        if (info != null) {
            for (int i = 0; i < info.getChildCount(); i++) {
                AccessibilityNodeInfo child = info.getChild(i);
                if (child != null) {
                    if (child.getChildCount() > 0) {
                        findViewAndClick(child, text);
                    } else {
                        if (TextUtils.isEmpty(text)) {
                        } else {
                            if (getContent(child).contains(text)) {
                                Logger.d(TAG, "findViewAndClick: + " + getContent(child));
                                child.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            }
                        }
                    }
                }
            }
        } else {
//            Logger.d(TAG, "findViewAndClick: info = null");
        }
    }


    /**
     * 查找需要点击的红包按钮
     */
    private List<AccessibilityNodeInfo> findNeedClickBtns() {
        List<AccessibilityNodeInfo> needList = new ArrayList<>();
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText("微信红包");
            if (nodes != null) {
                for (int i = 0; i < nodes.size(); i++) {
                    AccessibilityNodeInfo accessibilityNodeInfo = nodes.get(i);
                    if (accessibilityNodeInfo != null) {
                        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                        if (parent != null) {
                            if (!needList.contains(parent)) {
                                needList.add(parent);
                            }
                            for (int j = 0; j < parent.getChildCount(); j++) {
                                AccessibilityNodeInfo child = parent.getChild(j);
                                if (getContent(child).contains("已领取") || getContent(child).contains("已被领完")) {
                                    if (needList.contains(parent)) {
                                        needList.remove(parent);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return needList;
        }
        return needList;

    }




    private AccessibilityNodeInfo findOpenBtn() {
        AccessibilityNodeInfo info = getRootInActiveWindow();
        if (info == null) {
            return null;
        }
        for (int i = 0; i < info.getChildCount(); i++) {
            AccessibilityNodeInfo child = info.getChild(i);
            if (child != null) {
                String className = child.getClassName().toString();
                if (className.contains("Button")) {
                    return child;
                }
            }
        }
        return null;
    }


}
