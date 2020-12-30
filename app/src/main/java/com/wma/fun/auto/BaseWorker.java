package com.wma.fun.auto;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.wma.library.log.Logger;

import java.util.List;

/**
 * create by wma
 * on 2020/10/9 0009
 */
public abstract class BaseWorker {
    String TAG = "";
    public AccessibilityService mService;

    public BaseWorker(AccessibilityService service) {
        TAG = this.getClass().getSimpleName();
        mService = service;
    }

    public abstract void work(AccessibilityEvent event);

    public abstract AccessibilityServiceInfo getAccessibilityServiceInfo();


    public void checkWindowElement(AccessibilityNodeInfo info) {
        if (info != null) {
            for (int i = 0; i < info.getChildCount(); i++) {
                AccessibilityNodeInfo child = info.getChild(i);
                if (child != null) {
                    if (child.getChildCount() > 0) {
                        checkWindowElement(child);
                    } else {
                        Logger.d(TAG, "checkWindowElement: " + getContent(child) + "  " + child.getClassName());
                    }
                }
            }
        } else {
            Logger.d(TAG, "checkWindowElement: info = null");
        }
    }


    public String getContent(AccessibilityNodeInfo info) {
        if (info == null) {
            return "";
        }
        if (TextUtils.isEmpty(info.getText())) {
            return "";
        }
        return info.getText().toString();
    }

    public AccessibilityNodeInfo getRootInActiveWindow(){
        return mService.getRootInActiveWindow();
    }

    /**
     * @param text
     * @return
     */
    public AccessibilityNodeInfo findViewByText(String text) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText(text);
            if (nodes != null) {
                for (AccessibilityNodeInfo node : nodes) {
                    String content = getContent(node);
                    if (content.contains(text)) {
                        return node;
                    }
                }
            }
        }
        return null;
    }


    public void goClick(AccessibilityNodeInfo btn) {
        if (btn == null) {
            return;
        }
        List<AccessibilityNodeInfo.AccessibilityAction> actionList = btn.getActionList();
        for (int i = 0; i < actionList.size(); i++) {
            AccessibilityNodeInfo.AccessibilityAction action = actionList.get(i);
            int actionId = action.getId();
            if (actionId == AccessibilityNodeInfo.ACTION_CLICK) {
                btn.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            } else {
//                btn.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                goClick(btn.getParent());
            }
        }
    }
}
