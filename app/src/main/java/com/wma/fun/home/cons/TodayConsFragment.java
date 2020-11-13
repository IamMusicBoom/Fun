package com.wma.fun.home.cons;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wma.fun.R;
import com.wma.fun.data.UserSP;
import com.wma.fun.databinding.FragmentTodayConsBinding;
import com.wma.fun.home.cons.module.ConsModule;
import com.wma.fun.home.cons.module.TodayModule;
import com.wma.library.base.BaseFragment;
import com.wma.library.base.BaseLazyLoadFragment;
import com.wma.library.log.Logger;
import com.wma.library.utils.ConsUtils;
import com.wma.library.utils.TimeUtils;

import org.xutils.common.Callback;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * create by wma
 * on 2020/10/28 0028
 */
public class TodayConsFragment extends BaseLazyLoadFragment<TodayModule, FragmentTodayConsBinding> {
    OnBackgroundChange onBackgroundChange;
    String type;

    public TodayConsFragment(OnBackgroundChange onBackgroundChange,String type) {
        this.onBackgroundChange = onBackgroundChange;
        this.type = type;
    }

    @Override
    public String getTitleStr() {
        return null;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_today_cons;
    }

    @Override
    protected void loadLazyData() {
        super.loadLazyData();
        new ConsModule().loadConstellation(this, TextUtils.isEmpty(UserSP.getUserCons()) ? ConsUtils.getCurConstellation() : UserSP.getUserCons(), type);
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return mBinding.smartRefreshLayout;
    }

    @Override
    public void handleBySuccess(TodayModule result) {
        super.handleBySuccess(result);
        mBinding.setTodayModule(result);
        if (onBackgroundChange != null) {
            onBackgroundChange.curCons(result.getName());
        }
    }

    public interface OnBackgroundChange {
        void curCons(String cons);
    }
}
