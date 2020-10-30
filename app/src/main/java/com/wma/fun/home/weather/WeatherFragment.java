package com.wma.fun.home.weather;

import android.os.Bundle;
import android.view.View;

import com.wma.fun.R;
import com.wma.fun.databinding.FragmentWeatherBinding;
import com.wma.fun.home.weather.module.WeatherModule;
import com.wma.fun.home.weather.module.WeatherNetModule;
import com.wma.fun.home.weather.module.WidsModule;
import com.wma.library.base.BaseLazyLoadFragment;
import com.wma.library.log.Logger;

import java.util.List;

/**
 * create by wma
 * on 2020/10/30 0030
 */
public class WeatherFragment extends BaseLazyLoadFragment<WeatherModule, FragmentWeatherBinding> {

    @Override
    protected boolean canRefresh() {
        return false;
    }


    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void loadLazyData() {
        super.loadLazyData();
        new WeatherNetModule().getWeather(this,"苏州");
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mBinding.temperatureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.temperatureView.setTemperature(25,-20,10);
            }
        });
    }

    @Override
    public void handleBySuccess(WeatherModule result) {
        super.handleBySuccess(result);
        Logger.d(TAG, "handleBySuccess: " + result.getCity());
    }
}
