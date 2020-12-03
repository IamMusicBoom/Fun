package com.wma.fun.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wma.fun.R;
import com.wma.fun.databinding.FragmentLoginBinding;
import com.wma.fun.login.module.LoginModule;
import com.wma.fun.main.MainActivity;
import com.wma.library.base.BaseLoadFragment;
import com.wma.library.log.Logger;
import com.wma.library.widget.views.SubmitBtn;

/**
 * create by wma
 * on 2020/12/3 0003
 */
public class LoginFragment extends BaseLoadFragment<LoginModule, FragmentLoginBinding> implements View.OnClickListener {
    private int mCurType;

    public LoginFragment(int type) {
        mCurType = type;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if (mCurType == LoginActivity.LOGIN) {
            mBinding.btnLogin.setText("登录");
            mBinding.tvDoNotLogin.setText("不想登录");
            mBinding.tvRegister.setText("注册");
        } else if (mCurType == LoginActivity.REGISTER) {
            mBinding.btnLogin.setText("注册");
            mBinding.tvDoNotLogin.setText("返回");
            mBinding.tvRegister.setVisibility(View.GONE);
        }
        mBinding.btnLogin.setOnClickListener(this);
        mBinding.tvRegister.setOnClickListener(this);
        mBinding.tvDoNotLogin.setOnClickListener(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onClick(View v) {
        if (mCurType == LoginActivity.LOGIN) {
            if (v == mBinding.tvRegister) {// 注册
                ((LoginActivity) getActivity()).mBinding.viewPager.setCurrentItem(1, true);
            } else if (v == mBinding.tvDoNotLogin) {// 不想登录
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else if (v == mBinding.btnLogin) {//登录
                mBinding.btnLogin.setCurStatus(SubmitBtn.LOADING);
                // TODO 登录
            }
        } else if (mCurType == LoginActivity.REGISTER) {
            if (v == mBinding.tvDoNotLogin) {// 返回登录
                ((LoginActivity) getActivity()).mBinding.viewPager.setCurrentItem(0, true);
            } else if (v == mBinding.btnLogin) {//注册
                mBinding.btnLogin.setCurStatus(SubmitBtn.LOADING);
                // TODO 注册
            }
        }
    }
}
