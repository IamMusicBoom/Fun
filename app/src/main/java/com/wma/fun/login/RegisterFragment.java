package com.wma.fun.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wma.fun.R;
import com.wma.fun.data.UserSP;
import com.wma.fun.databinding.FragmentResisterBinding;
import com.wma.fun.login.module.User;
import com.wma.library.base.BaseLoadFragment;
import com.wma.library.utils.RegexUtils;
import com.wma.library.widget.views.SubmitBtn;

/**
 * create by wma
 * on 2020/12/4 0004
 */
public class RegisterFragment extends BaseLoadFragment<User, FragmentResisterBinding> implements View.OnClickListener {
    private String mPhone;
    private String mEmail;
    private String mAccount;
    private String mPassword;

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return null;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mBinding.btnRegister.setOnClickListener(this);
        mBinding.tvGoLogin.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_resister;
    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.tvGoLogin) {
            ((LoginActivity) getActivity()).mBinding.viewPager.setCurrentItem(0, true);
        } else if (v == mBinding.btnRegister) {
            if (canRegister()) {
                register();
            }
        }

    }

    private boolean canRegister() {
        mAccount = mBinding.etAccount.getText().toString();
        mPassword = mBinding.etPassword.getText().toString();
        mPhone = mBinding.etPhone.getText().toString();
        mEmail = mBinding.etEmail.getText().toString();
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
        if (TextUtils.isEmpty(mPhone) && TextUtils.isEmpty(mEmail)) {
            mBinding.tilEmail.setError("需要填写一个手机号码，或者邮件");
            mBinding.tilPhone.setError("需要填写一个手机号码，或者邮件");
            return false;
        }
        if(!RegexUtils.isEmail(mEmail)){
            mBinding.tilEmail.setError("邮件格式不正确");
            return false;
        }
        if(!RegexUtils.isMobile(mPhone)){
            mBinding.tilEmail.setError("手机格式不正确");
            return false;
        }
        return true;
    }

    private void register() {
        mBinding.btnRegister.setCurStatus(SubmitBtn.LOADING);
        new User().register(this, mAccount, mPassword, mEmail, mPhone);
    }

    @Override
    public void handleByFail(String msg) {
        super.handleByFail(msg);
        mBinding.btnRegister.setFailText(msg);
        mBinding.btnRegister.setCurStatus(SubmitBtn.FAIL);
    }

    @Override
    public void handleBySuccess(User user) {
        super.handleBySuccess(user);
        mBinding.btnRegister.setCurStatus(SubmitBtn.SUCCESS);
        UserSP.putUser(user);
        UserSP.putToken(user.getToken());
        ((LoginActivity) getActivity()).mBinding.viewPager.setCurrentItem(0, true);
    }
}
