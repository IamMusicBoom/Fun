package com.wma.fun.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wma.fun.R;
import com.wma.fun.databinding.ActivityLoginBinding;
import com.wma.fun.main.MainActivity;
import com.wma.library.base.BaseActivity;
import com.wma.library.log.Logger;
import com.wma.library.utils.ScreenUtils;

/**
 * create by wma
 * on 2020/10/21 0021
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//    }

        @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },1000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}
