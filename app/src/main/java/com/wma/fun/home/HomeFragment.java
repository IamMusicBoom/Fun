package com.wma.fun.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wma.fun.R;
import com.wma.fun.databinding.FragmentHomeBinding;
import com.wma.fun.home.cons.TodayConsFragment;
import com.wma.fun.home.cons.module.ConsModule;
import com.wma.fun.home.weather.WeatherFragment;
import com.wma.library.base.BaseFragment;
import com.wma.library.base.BaseFragmentPagerAdapter;
import com.wma.library.utils.ConsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/10/22 0022
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements TodayConsFragment.OnBackgroundChange {


    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        initWeather();
        initCons();
    }

    /**
     * 初始化天气
     */
    private void initWeather() {
        List<Fragment> mWeatherFragments = new ArrayList<>();
        mWeatherFragments.add(new WeatherFragment());
        mBinding.weatherPager.setAdapter(new BaseFragmentPagerAdapter(getChildFragmentManager(), mWeatherFragments));
    }

    /**
     * 初始化星座
     */
    private void initCons() {
        List<Fragment> mConsFragments = new ArrayList<>();
        mConsFragments.add(new TodayConsFragment(this, ConsModule.TYPE_TODAY));
        mConsFragments.add(new TodayConsFragment(this, ConsModule.TYPE_TOMORROW));
        mBinding.consPager.setAdapter(new BaseFragmentPagerAdapter(getChildFragmentManager(), mConsFragments));
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void curCons(String cons) {
        mBinding.setCons(cons);
    }
}
