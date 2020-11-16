package com.wma.fun.home.news;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.wma.fun.R;
import com.wma.fun.databinding.ActivityNewsListBinding;
import com.wma.library.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public class NewsListActivity extends BaseActivity<ActivityNewsListBinding> {
    List<Fragment> mFragments = new ArrayList<>();

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        for (int i = 0; i < NewsModule.mValue.length; i++) {
            mFragments.add(NewsListFragment.newInstance(NewsModule.mValue[i]));
            mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(NewsModule.mName[i]));
        }
        mBinding.viewPager.setAdapter(new NewsPagerAdapter(getSupportFragmentManager(), mFragments));
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_list;
    }
}
