package com.wma.fun.home;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wma.fun.R;
import com.wma.fun.databinding.FragmentHomeBinding;
import com.wma.library.base.BaseFragment;
import com.wma.library.log.Logger;

/**
 * create by wma
 * on 2020/10/22 0022
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {





    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init() {



    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
}
