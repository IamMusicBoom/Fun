package com.wma.fun.home.cons;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.wma.fun.R;
import com.wma.library.log.Logger;
import com.wma.library.utils.DPUtils;


/**
 * create by wma
 * on 2020/9/22 0022
 */
public class ProgressView extends View {
    final String TAG = ProgressView.class.getSimpleName();

    private Paint mPaint;

    int mWidth, mHeight;

    int mProgressWith;

    private Context mContext;

    private int mStrokeWidth = 1;

    private RectF mBgRect, mPgRect;

    private float mStartX, mStartY, mEndX, mEndY;

    private float mProgress;

    private float mMax = 100f;


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mProgressWith = DPUtils.dip2px(mContext, 20);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));


        mBgRect = new RectF();
        mPgRect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaintForBg();

        drawBg(canvas);

        initPaintForProgress();

        drawProgress(canvas);

    }

    /**
     * 初始化进度画笔
     */
    private void initPaintForProgress() {
        mPaint.setStyle(Paint.Style.FILL);
        LinearGradient mLinearShader = new LinearGradient(mStartX, mStartY, mEndX, mEndY, ContextCompat.getColor(mContext, R.color.colorProgressStart), ContextCompat.getColor(mContext, R.color.colorProgressEnd), Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearShader);
    }

    /**
     * 画进度
     *
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        mPgRect.set(mBgRect.left, mBgRect.top, mProgress, mBgRect.bottom);
        canvas.drawRoundRect(mPgRect, 50, 50, mPaint);
    }


    /**
     * 初始化背景画笔
     */
    private void initPaintForBg() {
        mPaint.setStyle(Paint.Style.STROKE);
        LinearGradient mLinearShader = new LinearGradient(mStartX, mStartY, mEndX, mEndY, ContextCompat.getColor(mContext, R.color.colorProgressStart), ContextCompat.getColor(mContext, R.color.colorProgressEnd), Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearShader);
    }

    /**
     * 画背景
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        canvas.drawRoundRect(mBgRect, 50, 50, mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mWidth > mHeight) { // 横着摆放，progressWidth 应该等于控件高度
            mHeight = mProgressWith;
        } else {// 竖着摆放，progressWidth 应该等于控件宽度
            mWidth = mProgressWith;
        }
        setMeasuredDimension(mWidth, mHeight);

        mBgRect.set(getPaddingStart(), getPaddingTop(), mWidth - getPaddingEnd(), mHeight - getPaddingBottom());
        mStartX = getPaddingStart();
        mStartY = mHeight / 2;
        mEndX = mWidth - getPaddingEnd();
        mEndY = mHeight / 2;
        if (mProgress == 0) {
            mProgress = mStartX;
        }


    }


    public void setMax(float max) {
        this.mMax = max;
    }

    public void updateProgress(float progress) {
        this.mProgress = progress / mMax * (mEndX - mStartX) + mStartX;
        postInvalidate();
    }

    public void setProgress(float progress) {
        ValueAnimator anim = ValueAnimator.ofFloat(mStartX, progress);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = ((float) animation.getAnimatedValue()) / mMax * (mEndX - mStartX);
                if(mProgress < mStartX){
                    mProgress = mStartX;
                }
                postInvalidate();
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(1000);
        anim.start();

    }

}
