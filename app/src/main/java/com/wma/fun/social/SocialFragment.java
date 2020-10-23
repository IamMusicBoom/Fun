package com.wma.fun.social;

import android.os.Bundle;

import com.wma.fun.R;
import com.wma.fun.databinding.FragmentHomeBinding;
import com.wma.library.base.BaseFragment;

/**
 * create by wma
 * on 2020/10/22 0022
 */
public class SocialFragment extends BaseFragment<FragmentHomeBinding> {
    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_social;
    }

}
