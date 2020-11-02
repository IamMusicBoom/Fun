package com.wma.fun.home.weather;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.wma.fun.R;
import com.wma.fun.databinding.FragmentWeatherBinding;
import com.wma.fun.home.weather.module.WeatherModule;
import com.wma.fun.home.weather.module.WeatherNetModule;
import com.wma.fun.utils.LocateUtils;
import com.wma.library.base.BaseLazyLoadFragment;
import com.wma.library.log.Logger;
import com.wma.library.utils.FileUtils;

/**
 * create by wma
 * on 2020/10/30 0030
 */
public class WeatherFragment extends BaseLazyLoadFragment<WeatherModule, FragmentWeatherBinding> {
    private String mCurDistrict;
    private String mCurCity;
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
        Logger.d(TAG, "loadLazyData: mCurDistrict = " + mCurDistrict);
        if(!TextUtils.isEmpty(mCurDistrict)){
            new WeatherNetModule().getWeather(this,mCurDistrict);
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
    }

    @Override
    public void handleBySuccess(WeatherModule result) {
        super.handleBySuccess(result);
        mBinding.setWeatherModule(result);
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
