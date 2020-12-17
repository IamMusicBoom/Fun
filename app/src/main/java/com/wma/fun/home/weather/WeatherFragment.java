package com.wma.fun.home.weather;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wma.fun.R;
import com.wma.fun.databinding.FragmentWeatherBinding;
import com.wma.fun.home.HomeFragment;
import com.wma.fun.home.news.NewsListActivity;
import com.wma.fun.home.weather.module.WeatherModule;
import com.wma.fun.home.weather.module.WeatherNetModule;
import com.wma.fun.utils.LocateUtils;
import com.wma.library.base.BaseFragmentPagerAdapter;
import com.wma.library.base.BaseLazyLoadFragment;
import com.wma.library.log.Logger;

/**
 * create by wma
 * on 2020/10/30 0030
 */
public class WeatherFragment extends BaseLazyLoadFragment<WeatherModule, FragmentWeatherBinding> {
    private String mCurDistrict;
    private String mCurCity;


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
        Logger.d(TAG, "loadLazyData: mCurDistrict = " + mCurDistrict);
        if(!TextUtils.isEmpty(mCurDistrict)){
            new WeatherNetModule().loadWeather(this,mCurDistrict);
        }
    }





    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        new LocateUtils().startLocate(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                String district = aMapLocation.getDistrict();
                String city = aMapLocation.getCity();
                StringBuilder districtSb = new StringBuilder();
                for (int i = 0; i < district.length(); i++) {
                    char c = district.charAt(i);
                    if(String.valueOf(c).equals("市") || String.valueOf(c).equals("县")
                            || String.valueOf(c).equals("区") || String.valueOf(c).equals("镇")){
                        continue;
                    }
                    districtSb.append(c);
                }


                StringBuilder citySb = new StringBuilder();
                for (int i = 0; i < city.length(); i++) {
                    char c = city.charAt(i);
                    if(String.valueOf(c).equals("市") || String.valueOf(c).equals("县")
                            || String.valueOf(c).equals("区") || String.valueOf(c).equals("镇")){
                        continue;
                    }
                    citySb.append(c);
                }
                mCurCity = citySb.toString();
                mCurDistrict = districtSb.toString();
                Logger.d(TAG, "onLocationChanged: mCurDistrict = " + mCurDistrict + " mCurCity = " + mCurCity);
                loadLazyData();

            }
        },null);
        mBinding.weatherImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewsListActivity.class));
            }
        });
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
    public void handleBySuccess(WeatherModule result) {
        super.handleBySuccess(result);
        mBinding.setWeatherModule(result);
        ((FutureFragment) ((BaseFragmentPagerAdapter) ((HomeFragment) getParentFragment()).mBinding.weatherPager.getAdapter()).getItem(1)).setWeatherModule(result);
    }

    @Override
    public void handleByFail(String msg) {
        super.handleByFail(msg);
        if("207301".equals(msg)){
            mCurDistrict = mCurCity;
            loadLazyData();
        }
    }
}
