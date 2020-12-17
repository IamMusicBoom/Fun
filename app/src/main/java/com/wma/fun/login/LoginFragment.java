package com.wma.fun.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wma.fun.R;
import com.wma.fun.data.UserSP;
import com.wma.fun.databinding.FragmentLoginBinding;
import com.wma.fun.login.module.User;
import com.wma.fun.main.MainActivity;
import com.wma.library.base.BaseLoadFragment;
import com.wma.library.utils.RegexUtils;
import com.wma.library.widget.views.SubmitBtn;

/**
 * create by wma
 * on 2020/12/3 0003
 */
public class LoginFragment extends BaseLoadFragment<User, FragmentLoginBinding> implements View.OnClickListener {
    private String mAccount, mPassword;

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
        mBinding.btnLogin.setOnClickListener(this);
        mBinding.tvRegister.setOnClickListener(this);
        mBinding.tvDoNotLogin.setOnClickListener(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = UserSP.getUser();
        if(user != null){
            String account = user.getAccount();
            if(!TextUtils.isEmpty(account)){
                mBinding.etAccount.setText(account);
                mBinding.etAccount.setSelection(account.length()-1);
            }
            String password = user.getPassword();
            if(!TextUtils.isEmpty(password)){
                mBinding.etPassword.setText(password);
                mBinding.etPassword.setSelection(password.length()-1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.tvRegister) {// 注册
            ((LoginActivity) getActivity()).mBinding.viewPager.setCurrentItem(1, true);
        } else if (v == mBinding.tvDoNotLogin) {// 不想登录
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else if (v == mBinding.btnLogin) {//登录
            if (canLogin()) {
                goLogin();
            }


        }
    }

    private void goLogin() {
        mBinding.btnLogin.setCurStatus(SubmitBtn.LOADING);
        new User().login(this, mAccount, mPassword);
    }

    @Override
    public void handleBySuccess(User user) {
        super.handleBySuccess(user);
        mBinding.btnLogin.setCurStatus(SubmitBtn.SUCCESS);
        UserSP.putUser(user);
        UserSP.putToken(user.getToken());
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void handleByFail(String msg) {
        super.handleByFail(msg);
        mBinding.btnLogin.setFailText(msg);
        mBinding.btnLogin.setCurStatus(SubmitBtn.FAIL);
    }

    private boolean canLogin() {
        mAccount = mBinding.etAccount.getText().toString();
        mPassword = mBinding.etPassword.getText().toString();
        if (TextUtils.isEmpty(mAccount)) {
            mBinding.tilAccount.setError("账号不能为空");
            return false;
        }
        for (int i = 0; i < mAccount.length(); i++) {
            char c = mAccount.charAt(i);
            if(RegexUtils.isChinese(String.valueOf(c))){
                mBinding.tilAccount.setError("账号中不能有中文字符");
                return false;
            }
        }
        if (TextUtils.isEmpty(mPassword)) {
            mBinding.tilPassword.setError("密码不能为空");
            return false;
        }
        if (mPassword.length() < 6) {
            mBinding.tilPassword.setError("密码长度>=6个字符");
            return false;
        }
        for (int i = 0; i < mPassword.length(); i++) {
            char c = mPassword.charAt(i);
            if(RegexUtils.isChinese(String.valueOf(c))){
                mBinding.tilPassword.setError("密码中不能含有中文字符");
                return false;
            }
        }
        if(RegexUtils.isContainsSpecial(mPassword)){
            mBinding.tilPassword.setError("密码中不能含有特殊字符");
            return false;
        }
        return true;
    }
}
