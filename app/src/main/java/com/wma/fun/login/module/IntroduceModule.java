package com.wma.fun.login.module;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.wma.library.base.BasePagerAdapter;
import com.wma.library.base.BasePagerTransformer;

import java.util.List;

/**
 * create by wma
 * on 2020/12/1 0001
 */
public class IntroduceModule {

    private List<View> views;

    public IntroduceModule(List<View> views) {
        this.views = views;
    }

    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    @BindingAdapter("introducePages")
    public static void introducePages(ViewPager pager, List<View> views) {
        pager.setPageTransformer(true, new BasePagerTransformer());
        pager.setAdapter(new BasePagerAdapter(views));
    }
}
