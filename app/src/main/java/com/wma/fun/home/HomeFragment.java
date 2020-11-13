package com.wma.fun.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.wma.fun.R;
import com.wma.fun.databinding.FragmentHomeBinding;
import com.wma.fun.home.cons.TodayConsFragment;
import com.wma.fun.home.cons.module.ConsModule;
import com.wma.fun.home.news.NewsFragment;
import com.wma.fun.home.weather.FutureFragment;
import com.wma.fun.home.weather.WeatherFragment;
import com.wma.fun.home.weather.module.WeatherModule;
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
//        initWeather();
//        initCons();
        initNews();
    }

    /**
     * 初始化新闻
     */
    private void initNews() {
        NewsFragment newsFragment = new NewsFragment();
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.news_card_view, newsFragment, NewsFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    /**
     * 初始化天气
     */
    private void initWeather() {
        List<Fragment> mWeatherFragments = new ArrayList<>();
        mWeatherFragments.add(new WeatherFragment());
        mWeatherFragments.add(new FutureFragment());
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
