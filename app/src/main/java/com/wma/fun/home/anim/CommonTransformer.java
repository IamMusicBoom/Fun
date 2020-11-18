package com.wma.fun.home.anim;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.wma.library.log.Logger;

/**
 * create by wma
 * on 2020/11/18 0018
 */
public class CommonTransformer implements ViewPager.PageTransformer {
    final String TAG = CommonTransformer.class.getSimpleName();
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(@NonNull View page, float position) {
//        if (position <= 0f) {
//            page.setTranslationX(0f);
//            page.setScaleX(1f);
//            page.setScaleY(1f);
//        } else if (position <= 1f) {
//            final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
//            page.setAlpha(1 - position);
//            page.setPivotY(0.5f * page.getHeight());
//            page.setTranslationX(page.getWidth() * -position);
//            page.setScaleX(scaleFactor);
//            page.setScaleY(scaleFactor);
//        }
        page.setAlpha(1 - Math.abs(position));
        page.setScaleX(1 - Math.abs(position));
        page.setScaleY(1 - Math.abs(position));
    }
}
