package com.wma.fun;

import com.wma.library.base.BasePermissionActivity;

/**
 * create by wma
 * on 2020/10/21 0021
 */
public class IndexActivity extends BasePermissionActivity {

    @Override
    public void beforeSetContentView() {
        super.beforeSetContentView();
        setNeedCheckPermission(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_index;
    }


    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init() {

    }
}
