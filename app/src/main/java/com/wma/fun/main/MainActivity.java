package com.wma.fun.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.wma.fun.R;
import com.wma.fun.data.UserSP;
import com.wma.fun.databinding.ActivityMainBinding;
import com.wma.fun.home.HomeFragment;
import com.wma.fun.login.LoginActivity;
import com.wma.fun.login.UpdateUserInfoActivity;
import com.wma.fun.login.module.User;
import com.wma.fun.social.SocialFragment;
import com.wma.fun.task.TaskFragment;
import com.wma.library.base.BaseActivity;
import com.wma.library.base.BasePagerTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener {
    private MainPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    ImageView mHeadImg;
    TextView mUserNameTv;

    @Override
    public String getTitleStr() {
        return null;
    }

    public void init(Bundle savedInstanceState) {
        // note : 沉浸状态栏，使DrawerLayout有覆盖状态栏的效果，状态栏必须是透明色，只能在子类布局在无title的情况下将布局延伸到状态栏，并且添加一个假的状态栏
        immerseStatus(true, false, Color.TRANSPARENT, 0);
        immerseStatusWithDrawerLayout(mBinding.drawerLayout, ContextCompat.getColor(this, R.color.colorAccent), 0);
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new SocialFragment());
        mFragments.add(new TaskFragment());
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setPageTransformer(true, new BasePagerTransformer());
        mBinding.viewPager.setOffscreenPageLimit(3);
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
                switch (itemId) {
                    case R.id.menu_home:
                        mBinding.viewPager.setCurrentItem(0, true);
                        break;
                    case R.id.menu_social:
                        mBinding.viewPager.setCurrentItem(1, true);
                        break;
                    case R.id.menu_task:
                        mBinding.viewPager.setCurrentItem(2, true);
                        break;
                }
                return false;
            }
        });
        View headerView = mBinding.navigationView.inflateHeaderView(R.layout.nva_header);
        if (UserSP.isLogin()) {
            mHeadImg = headerView.findViewById(R.id.img_head);
            mHeadImg.setOnClickListener(this);
        }

        mUserNameTv = headerView.findViewById(R.id.tv_user_name);
        mUserNameTv.setOnClickListener(this);
        if (UserSP.isLogin()) {
            User user = UserSP.getUser();
            mUserNameTv.setText(TextUtils.isEmpty(user.getUserName()) ? "完善用户资料" : user.getUserName());
        }
        if (!UserSP.isLogin()) {
            mUserNameTv.setText("登录/注册");
        }
        mBinding.navigationView.inflateMenu(R.menu.drawer_menu);
        mBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_menu_luck_money) {

                } else if (menuItem.getItemId() == R.id.nav_menu_settings) {
                }
                mBinding.drawerLayout.closeDrawers();
                return true;
            }
        });

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == mUserNameTv) {
            if (UserSP.isLogin()) {
                Intent intent = new Intent(MainActivity.this, UpdateUserInfoActivity.class);
//                Pair<View, String> pHead = Pair.create((View)mHeadImg, "transitionHead");
//                Pair<View, String> pName = Pair.create((View)mUserNameTv,"transitionName");
//                Bundle compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, pHead, pName).toBundle();
                Bundle compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,mUserNameTv,"transitionName").toBundle();
                startActivity(intent, compat);
            } else {
                LoginActivity.goLogin(MainActivity.this);
            }
        } else if (v == mHeadImg) {
            // TODO 查看头像大图
        }
    }
}
