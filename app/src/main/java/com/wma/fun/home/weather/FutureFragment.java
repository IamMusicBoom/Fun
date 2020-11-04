package com.wma.fun.home.weather;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.wma.fun.R;
import com.wma.fun.databinding.FragmentFutureBinding;
import com.wma.fun.home.weather.module.FutureTemperature;
import com.wma.fun.home.weather.module.WeatherModule;
import com.wma.library.base.BaseFragment;
import com.wma.library.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    public void setWeatherModule(WeatherModule result) {
        List<WeatherModule.FutureBean> future = result.getFuture();

        List<FutureTemperature> list = new ArrayList<>();
        if(future != null){
            for (int i = 0; i < future.size(); i++) {
                WeatherModule.FutureBean futureBean = future.get(i);
                if(futureBean != null){
                    String temperature = futureBean.getTemperature();
                    String[] split = temperature.split("/");
                    if(split != null && split.length>=2){
                        String maxT = StringUtils.findNumberInStr(split[1]);
                        StringBuilder date = new StringBuilder();
                        String[] split1 = futureBean.getDate().split("-");
                        if(split1 != null && split1.length>=3){
                            date.append(split1[1]).append("-").append(split1[2]);
                            list.add(new FutureTemperature(maxT, split[0], date.toString()));
                        }
                    }
                }
            }
        }
        mBinding.futureView.setFutureList(list);

    }
}
