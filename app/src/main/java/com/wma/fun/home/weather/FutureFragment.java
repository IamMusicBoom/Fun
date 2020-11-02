package com.wma.fun.home.weather;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.wma.fun.R;
import com.wma.fun.databinding.FragmentFutureBinding;
import com.wma.library.base.BaseFragment;

/**
 * create by wma
 * on 2020/11/2 0002
 */
public class FutureFragment extends BaseFragment<FragmentFutureBinding> {
    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_future;
    }
}
