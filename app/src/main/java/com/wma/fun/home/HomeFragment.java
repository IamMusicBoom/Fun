package com.wma.fun.home;

import android.os.Bundle;

import com.wma.fun.R;
import com.wma.fun.databinding.FragmentHomeBinding;
import com.wma.library.base.BaseFragment;
import com.wma.library.base.BaseLoadFragment;
import com.wma.library.log.Logger;

/**
 * create by wma
 * on 2020/10/22 0022
 */
public class HomeFragment extends BaseLoadFragment<FragmentHomeBinding> {





    @Override
    public String getTitleStr() {
        return null;
    }


    @Override
    protected void loadData() {
        Logger.d(TAG, "loadData: ");
        new HomeModule().loadConstellation(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

}
