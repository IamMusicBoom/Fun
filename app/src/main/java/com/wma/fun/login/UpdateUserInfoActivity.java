package com.wma.fun.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wma.fun.R;
import com.wma.fun.data.UserSP;
import com.wma.fun.databinding.ActivityUpdateUserInfoBinding;
import com.wma.fun.login.module.User;
import com.wma.library.base.BaseLoadActivity;
import com.wma.library.select.FileItem;
import com.wma.library.select.FileType;
import com.wma.library.select.SelectDialog;
import com.wma.library.log.Logger;
import com.wma.library.utils.RegexUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * create by wma
 * on 2020/12/7 0007
 */
public class UpdateUserInfoActivity extends BaseLoadActivity<User, ActivityUpdateUserInfoBinding> {
    private String mEmail, mPhone;


    @Override
    protected void loadData() {
        User user = UserSP.getUser();
        if (user == null) {
            return;
        }
        new User().getUserById(this, user.getId());
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return findViewById(R.id.smart_refresh_layout);
    }

    @Override
    public String getTitleStr() {

        return "编辑资料";
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        loadData();
        mTitleBar.setRightText("完成");
        mBinding.rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                User user = mBinding.getUser();
                if (checkedId == R.id.rb_male) {
                    user.setSex(0);
                } else if (checkedId == R.id.rb_female) {
                    user.setSex(1);
                }
            }
        });
        mBinding.imgBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = mBinding.getUser();
                if (user.getBirthday() == null) {
                    showTimePickerDialog(System.currentTimeMillis(), "选择生日", true);
                } else {
                    showTimePickerDialog(user.getBirthday(), "选择生日", true);

                }
            }
        });
        User result = new User();
        result.setUserName("3");
        mBinding.setUser(result);
        mBinding.imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mBinding.etUserName.getText().toString();
                String type = FileType.FILE;
                switch (Integer.valueOf(s)) {
                    case 1:
                        type = FileType.FILE;
                        break;
                    case 2:
                        type = FileType.AUDIO;
                        break;
                    case 3:
                        type = FileType.IMAGE;
                        break;
                    case 4:
                        type = FileType.VIDEO;
                        break;
                }
                SelectDialog selectDialog = new SelectDialog.Builder()
                        .setLimit(5)
                        .setType(type)
                        .create();
                selectDialog.show(getSupportFragmentManager(), SelectDialog.class.getSimpleName());
            }
        });
    }


    /**
     * 选择时间
     */
    DatePickerDialog mDatePickerDialog;

    public void showTimePickerDialog(Long select,String title,boolean isOnlyDay) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        if(mDatePickerDialog == null){
            mDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(year, month, dayOfMonth);
                    mBinding.getUser().setBirthday(calendar1.getTimeInMillis());
                    mDatePickerDialog.dismiss();
                }
            }, year, monthOfYear, dayOfMonth);
            mDatePickerDialog.setTitle(title);
        }
        mDatePickerDialog.show();
    }

    @Override
    public void onRightLlClick(View view) {
        super.onRightLlClick(view);
        User user = mBinding.getUser();
        if (canSubmit()) {
            submit();
        }
    }

    private void submit() {
        new User().updateUser(this, mBinding.getUser());
    }

    private boolean canSubmit() {
        mEmail = mBinding.etUserEmail.getText().toString();
        mPhone = mBinding.etUserPhone.getText().toString();
        if (!RegexUtils.isEmail(mEmail)) {
            mBinding.tilUserEmail.setError("邮件格式不正确");
            return false;
        }
        if (!RegexUtils.isMobile(mPhone)) {
            mBinding.tilUserPhone.setError("手机格式不正确");
            return false;
        }
        return true;
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected boolean enableRefresh() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_user_info;
    }

    @Override
    public void handleBySuccess(User result) {
        super.handleBySuccess(result);
        if (mBinding.getUser() == null) {
            mBinding.setUser(result);
        } else {
            UserSP.putUser(result);
            showToast("修改成功");
            onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        setEnableRefresh(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SelectDialog.REQUEST_CODE && data != null) {// 选择图片
                ArrayList<FileItem> list = data.getParcelableArrayListExtra(SelectDialog.KEY_SELECT_LIST);
                if (list != null) {

                }
            }
        }
    }
}
