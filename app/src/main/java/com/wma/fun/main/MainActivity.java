package com.wma.fun.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.wma.fun.R;
import com.wma.fun.databinding.ActivityMainBinding;
import com.wma.library.base.BaseActivity;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener {



    @Override
    public String getTitleStr() {
        return null;
    }

    public void init(Bundle savedInstanceState) {

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
