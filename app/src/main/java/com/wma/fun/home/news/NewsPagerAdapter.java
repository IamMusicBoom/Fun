package com.wma.fun.home.news;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.wma.library.base.BaseFragmentPagerAdapter;
import com.wma.library.base.BasePagerAdapter;

import java.util.List;

/**
 * create by wma
 * on 2020/11/16 0016
 */
public class NewsPagerAdapter extends BaseFragmentPagerAdapter {


    public NewsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, fragments);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return NewsModule.mName[position];
    }
}
