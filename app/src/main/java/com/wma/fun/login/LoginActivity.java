package com.wma.fun.login;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.wma.fun.R;
import com.wma.fun.databinding.ActivityLoginBinding;
import com.wma.library.base.BaseActivity;
import com.wma.library.base.BaseFragmentPagerAdapter;
import com.wma.library.base.BasePagerTransformer;
import com.wma.library.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    public static final int LOGIN = 0;
    public static final int REGISTER = 1;

    @Override
    public String getTitleStr() {
        return null;
    }


    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initBackground();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        mBinding.viewPager.setPageTransformer(true, new BasePagerTransformer());
        mBinding.viewPager.setAdapter(adapter);
    }

    private void initBackground() {
        Window window = getWindow();
        StringBuilder sb = new StringBuilder();
        int page = (int) ((Math.random() * (10 + 1)));
        sb.append("page");
        sb.append(page);
        sb.append(".jpg");
        String assetsFilePath = new FileUtils(this).getAssetsFilePath(sb.toString());
        Bitmap bitmap = BitmapFactory.decodeFile(assetsFilePath);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        window.setBackgroundDrawable(drawable);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    public static void goLogin(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

}
