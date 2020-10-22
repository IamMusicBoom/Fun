package com.wma.fun.main;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wma.fun.R;
import com.wma.fun.databinding.ActivityMainBinding;
import com.wma.fun.home.HomeFragment;
import com.wma.fun.social.SocialFragment;
import com.wma.fun.task.TaskFragment;
import com.wma.library.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener {
    private MainPagerAdapter mAdapter;
    private List<Fragment> mFragments;
    @Override
    public void onClick(View v) {

    }

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new SocialFragment());
        mFragments.add(new TaskFragment());
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(),mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBinding.bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                switch (itemId){
                    case R.id.menu_home:
                        mBinding.viewPager.setCurrentItem(0,true);
                        break;
                    case R.id.menu_social:
                        mBinding.viewPager.setCurrentItem(1,true);
                        break;
                    case R.id.menu_task:
                        mBinding.viewPager.setCurrentItem(2,true);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
