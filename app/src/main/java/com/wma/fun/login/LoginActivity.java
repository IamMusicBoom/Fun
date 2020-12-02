package com.wma.fun.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wma.fun.R;
import com.wma.fun.databinding.ActivityLoginBinding;
import com.wma.library.base.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}
