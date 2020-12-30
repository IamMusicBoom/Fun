package com.wma.fun.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.wma.library.base.BaseModule;
import com.wma.library.log.Logger;
import com.wma.library.select.FileItem;
import com.wma.library.select.FileType;
import com.wma.library.select.SelectDialog;
import com.wma.library.utils.ConsUtils;
import com.wma.library.utils.RegexUtils;
import com.wma.library.utils.gilde.GlideUtils;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * create by wma
 * on 2020/12/7 0007
 */
public class UpdateUserInfoActivity extends BaseLoadActivity<User, ActivityUpdateUserInfoBinding> implements View.OnClickListener {
    private String mEmail, mPhone;
    private final int REQUEST_CODE_HEAD_IMG = SelectDialog.REQUEST_CODE + 10;
    private final int REQUEST_CODE_BG_WALL_IMG = SelectDialog.REQUEST_CODE + 20;


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
        mBinding.imgHead.setOnClickListener(this);
    }


    /**
     * 选择时间
     */
    DatePickerDialog mDatePickerDialog;

    public void showTimePickerDialog(Long select, String title, boolean isOnlyDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(select);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        if (mDatePickerDialog == null) {
            mDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar selectCalendar = Calendar.getInstance();
                    selectCalendar.set(year, month, dayOfMonth);
                    mBinding.getUser().setBirthday(selectCalendar.getTimeInMillis());
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
        if (canSubmit()) {
            showLoading("");
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
            GlideUtils.getInstance().loadBlur(getApplicationContext(), result.getBgWall(), mBinding.imgBgWall, 20);
            GlideUtils.getInstance().loadCircle(getApplicationContext(), result.getHeadImage(), mBinding.imgHead);
        } else {
            UserSP.putUser(result);
            if (result.getBirthday() != null && result.getBirthday() > 0) {
                String constellation = ConsUtils.getConstellation(result.getBirthday());
                UserSP.putUserCons(constellation);
            }
            showToast("修改成功");
            setResult(RESULT_OK);
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
            if (requestCode == REQUEST_CODE_HEAD_IMG && data != null) {// 头像选择
                ArrayList<FileItem> list = data.getParcelableArrayListExtra(SelectDialog.KEY_SELECT_LIST);
                if (list != null && list.size() == 1) {
                    FileItem fileItem = list.get(0);
                    GlideUtils.getInstance().loadCircle(getApplicationContext(), fileItem.getFilePath(), mBinding.imgHead);
                    mBinding.getUser().setHeadImage(fileItem.getFilePath());
                }
            } else if (requestCode == REQUEST_CODE_BG_WALL_IMG && data != null) {// 背景墙选择
                ArrayList<FileItem> list = data.getParcelableArrayListExtra(SelectDialog.KEY_SELECT_LIST);
                if (list != null && list.size() == 1) {
                    FileItem fileItem = list.get(0);
                    GlideUtils.getInstance().loadBlur(getApplicationContext(), fileItem.getFilePath(), mBinding.imgBgWall, 20);
//                    GlideUtils.getInstance().loadMutilTransformatiom(getApplicationContext(),fileItem.getFilePath(),mBinding.imgBgWall,new BlurTransformation(20, 1), new RoundedCorners(10));
                    mBinding.getUser().setBgWall(fileItem.getFilePath());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.imgBirthday) {
            User user = mBinding.getUser();
            if (user.getBirthday() == null) {
                showTimePickerDialog(System.currentTimeMillis(), "选择生日", true);
            } else {
                showTimePickerDialog(user.getBirthday(), "选择生日", true);

            }
        } else if (v == mBinding.imgHead) {
            SelectDialog selectDialog = new SelectDialog.Builder()
                    .setLimit(1)
                    .setType(FileType.IMAGE)
                    .setRequestCode(REQUEST_CODE_HEAD_IMG)
                    .create();
            selectDialog.show(getSupportFragmentManager(), SelectDialog.class.getSimpleName());

        } else if (v == mBinding.imgBgWall) {
            SelectDialog selectDialog = new SelectDialog.Builder()
                    .setLimit(1)
                    .setType(FileType.IMAGE)
                    .setRequestCode(REQUEST_CODE_BG_WALL_IMG)
                    .create();
            selectDialog.show(getSupportFragmentManager(), SelectDialog.class.getSimpleName());
        }
    }
}
