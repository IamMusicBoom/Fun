package com.wma.fun.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wma.fun.R;
import com.wma.fun.databinding.ActivityLoginBinding;
import com.wma.library.base.BaseActivity;

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

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}
