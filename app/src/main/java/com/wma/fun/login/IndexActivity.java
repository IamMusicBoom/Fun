package com.wma.fun.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.annotation.Nullable;

import com.wma.fun.R;
import com.wma.fun.data.ConfigSP;
import com.wma.fun.data.UserSP;
import com.wma.fun.databinding.ActivityIndexBinding;
import com.wma.fun.main.MainActivity;
import com.wma.library.base.BaseActivity;
import com.wma.library.log.Logger;
import com.wma.library.utils.FileUtils;
import com.wma.library.utils.TimeUtils;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * create by wma
 * on 2020/11/23 0023
 */
public class IndexActivity extends BaseActivity<ActivityIndexBinding> {
    private int mMaxTime = 4;
    private int mCurTime = 0;

    @Override
    public String getTitleStr() {
        return null;
    }

    private Timer mTimer = new Timer();

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mTimer.schedule(new MyTask(), 200, 1000);
        mBinding.countDownView.setMaxTime(mMaxTime);
        initBackground();
    }

    private void initBackground() {
        int curDay = TimeUtils.getCurDay();
        Window window = getWindow();
        StringBuilder sb = new StringBuilder();
        if(curDay%2==0){
            int page = (int) ((Math.random() * (8 + 1)));
            sb.append("d_bg_page");
            sb.append(page);
            sb.append(".jpg");
        }else{
            int page = (int) ((Math.random() * (13 + 1)));
            sb.append("s_bg_page");
            sb.append(page);
            sb.append(".jpg");
        }
        Logger.d(TAG, "init: name = " + sb.toString());
        String assetsFilePath = new FileUtils(this).getAssetsFilePath(sb.toString());
        Bitmap bitmap = BitmapFactory.decodeFile(assetsFilePath);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        window.setBackgroundDrawable(drawable);
    }


    public Animation shakeAnimation(int CycleTimes) {
        AnimationSet set = new AnimationSet(true);
        Animation translateAnimation1 = new TranslateAnimation(0, 30, 0, 0);
        translateAnimation1.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation1.setDuration(50);

        Animation translateAnimation2 = new TranslateAnimation(30, 0, 0, 0);
        translateAnimation2.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation2.setDuration(50);
        translateAnimation2.setStartOffset(50);


        Animation translateAnimation3 = new TranslateAnimation(0, -30, 0, 0);
        translateAnimation3.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation3.setDuration(50);
        translateAnimation3.setStartOffset(100);

        Animation translateAnimation4 = new TranslateAnimation(-30, 0, 0, 0);
        translateAnimation4.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation4.setDuration(50);
        translateAnimation4.setStartOffset(150);


        set.addAnimation(translateAnimation1);
        set.addAnimation(translateAnimation2);
        set.addAnimation(translateAnimation3);
        set.addAnimation(translateAnimation4);
        set.setDuration(150);
        return set;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_index;
    }

    class MyTask extends TimerTask {

        @Override
        public void run() {
            mCurTime++;
            if (mCurTime % 2 == 0) {
                Animation set = shakeAnimation(5);
                mBinding.tvInfo.startAnimation(set);
            }
            mBinding.countDownView.setCurTime(mCurTime);
            if (mCurTime == mMaxTime) {
                mTimer.cancel();
                prepareGoSomeWhere();
                IndexActivity.this.finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    /**
     * 根据条件去某个地方
     */
    private void prepareGoSomeWhere() {
        boolean firstUse = ConfigSP.getFirstUse();
        Intent intent;
        if (firstUse) {
            intent = new Intent(IndexActivity.this, IntroduceActivity.class);
            startActivity(intent);
        } else {
            String token = UserSP.getToken();
            if (TextUtils.isEmpty(token)) {
                LoginActivity.goLogin(IndexActivity.this);
            } else {
                intent = new Intent(IndexActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
