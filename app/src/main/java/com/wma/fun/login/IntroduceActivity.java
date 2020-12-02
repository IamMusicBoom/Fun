package com.wma.fun.login;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wma.fun.R;
import com.wma.fun.data.ConfigSP;
import com.wma.fun.databinding.ActivityIntroduceBinding;
import com.wma.fun.login.module.IntroduceModule;
import com.wma.library.base.BaseActivity;
import com.wma.library.log.Logger;
import com.wma.library.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class IntroduceActivity extends BaseActivity<ActivityIntroduceBinding> implements View.OnClickListener{
    List<String> mDestPaths;

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        initViews();

        initIndicator();

    }

    private void initIndicator() {
        for (int i = 0; i < mDestPaths.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setButtonDrawable(R.drawable.select_indicator);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioButton.setPadding(10, 10, 10, 10);
            lp.leftMargin = 10;
            radioButton.setLayoutParams(lp);
            mBinding.rgIndicator.addView(radioButton);
            radioButton.setId(i);
            if (i == 0) {
                radioButton.setChecked(true);
            }
        }
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBinding.rgIndicator.check(position);
                mBinding.goLoginTv.setVisibility((position == mDestPaths.size() - 1) ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        List<View> views = new ArrayList<>();
        List<String> originPaths = new ArrayList<>();
        mDestPaths = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("page").append(i).append(".jpg");
            String assetsFilePath = new FileUtils(this).getAssetsFilePath(sb.toString());
            originPaths.add(assetsFilePath);
        }
        for (int i = 0; i < 3; i++) {
            int position = (int) (Math.random() * originPaths.size());
            String path = originPaths.remove(position);
            mDestPaths.add(path);
        }
        for (int i = 0; i < mDestPaths.size(); i++) {
            String path = mDestPaths.get(i);
            Logger.d(TAG, "init: path = " + path);
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageBitmap(BitmapFactory.decodeFile(path));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(imageView);
        }
        IntroduceModule introduceModule = new IntroduceModule(views);
        mBinding.setModule(introduceModule);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_introduce;
    }

    @Override
    public void onClick(View v) {
        if(v == mBinding.goLoginTv){
            Intent intent = new Intent(IntroduceActivity.this, LoginActivity.class);
            ConfigSP.putFirstUse(false);
            startActivity(intent);
            onBackPressed();
        }
    }
}
