package com.wma.fun.home.weather;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wma.library.utils.DPUtils;


/**
 * create by wma
 * on 2020/11/2 0002
 */
public class WindView extends View {
    private Context mContext;
    private Paint mPaint;
    private Paint mWindNamePaint;
    private int mStrokeWidth = 2;
    private int mWidth, mHeight;
    private int mBigRadius, mSmallRadius;
    private Path mWindPowerPath;
    private Rect mWindNameBounds;
    private String mCurWindName;
    private String mCurWindPower;
    private String[] mWindNames = {"北风", "东北风", "东风", "东南风", "南风", "西南风", "西风", "西北风"};
    private StringBuilder mSB;

    public WindView(Context context) {
        this(context, null);
    }

    public WindView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WindView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mBigRadius = DPUtils.dip2px(mContext, 80);
        mSmallRadius = mBigRadius / 10;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.GRAY);
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));


        mWindNamePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWindNamePaint.setAntiAlias(true);
        mWindNamePaint.setStyle(Paint.Style.STROKE);
        mWindNamePaint.setStrokeWidth(1);
        mWindNamePaint.setStrokeCap(Paint.Cap.ROUND);
        mWindNamePaint.setColor(Color.GRAY);
        mWindNamePaint.setTextAlign(Paint.Align.CENTER);
        mWindNamePaint.setTextSize(15);


        mWindNameBounds = new Rect();

        mWindPowerPath = new Path();

        mSB = new StringBuilder();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                mWidth = widthSize - getPaddingStart() - getPaddingEnd();
                break;
            case MeasureSpec.AT_MOST:
                mWidth = Math.min(widthSize, mBigRadius * 2) - getPaddingStart() - getPaddingEnd();
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                mHeight = heightSize - getPaddingTop() - getPaddingBottom();
                break;
            case MeasureSpec.AT_MOST:
                mHeight = Math.min(heightSize, mBigRadius * 2) - getPaddingTop() - getPaddingBottom();
                break;
        }
        mWidth = mHeight = Math.min(mWidth, mHeight);
        setMeasuredDimension(mWidth, mHeight);
        mBigRadius = mWidth / 2 - mStrokeWidth;
        mSmallRadius = mBigRadius / 10 - mStrokeWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mBigRadius, mPaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mSmallRadius, mPaint);


        drawWindNameAndPower(canvas);


    }

    private void drawWindNameAndPower(Canvas canvas) {
        for (int i = 0; i < mWindNames.length; i++) {
            String windName = mWindNames[i];
            mSB.setLength(0);
            mSB.append(windName);
            if (windName.equals(mCurWindName)) {
                mSB.append(" ");
                mSB.append(mCurWindPower);
            }
            mWindNamePaint.getTextBounds(mSB.toString(), 0, mSB.toString().length(), mWindNameBounds);
            canvas.drawText(mSB.toString(), mWidth / 2, 20 + (mWindNameBounds.bottom - mWindNameBounds.top) / 2, mWindNamePaint);
            mWindPowerPath.reset();
            if (windName.equals(mCurWindName)) {// 画当前风力
                mWindPowerPath.moveTo(mWidth / 2, mHeight / 2 - 2 * mSmallRadius);
                mWindPowerPath.lineTo(mWidth / 2, 30 + (mWindNameBounds.bottom - mWindNameBounds.top) / 2);
                int power = handleWindPower(mCurWindPower);
                if (power < 10) {
                    float y = 30 + (mWindNameBounds.bottom - mWindNameBounds.top) / 2;
                    float length = 0;
                    for (int j = 0; j < power; j++) {
                        length += 20;
                        if (length > 40) {
                            y = y + 10;
                            length = 20;
                        }
                        mWindPowerPath.moveTo(mWidth / 2f, y);
                        mWindPowerPath.lineTo(mWidth / 2f + length, y);
                    }
                } else {
                    float y = 30 + (mWindNameBounds.bottom - mWindNameBounds.top) / 2;
                    mWindPowerPath.moveTo(mWidth / 2f, y);
                    mWindPowerPath.lineTo(mWidth / 2f + 20, y);
                    y += 20;
                    mWindPowerPath.lineTo(mWidth / 2f, y);
                    int morePower = power - 10;
                    float length = 0;
                    y += 5;
                    for (int j = 0; j < morePower; j++) {
                        length += 20;
                        if (length > 40) {
                            y = y + 10;
                            length = 20;
                        }
                        mWindPowerPath.moveTo(mWidth / 2f, y);
                        mWindPowerPath.lineTo(mWidth / 2f + length, y);
                    }
                }
            }else{
                mWindPowerPath.moveTo(mWidth / 2, mHeight / 2 - 2 * mSmallRadius);
                mWindPowerPath.lineTo(mWidth / 2, 60 + (mWindNameBounds.bottom - mWindNameBounds.top) / 2);
            }
            canvas.drawPath(mWindPowerPath, mPaint);
            canvas.rotate(360 / mWindNames.length, mWidth / 2, mHeight / 2);
        }
    }

    public void setCurWindAndPower(String windName, String power) {
        this.mCurWindName = windName;
        this.mCurWindPower = power;
        invalidate();
    }

    private int handleWindPower(String power) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(power)) {
            return 0;
        }
        for (int i = 0; i < power.length(); i++) {
            char c = power.charAt(i);
            if ((int) c >= 48 && (int) c <= 57) {
                sb.append(c);
            }
        }
        if (sb.length() <= 0) {
            return 0;
        } else {
            return Integer.valueOf(sb.toString());
        }
    }
}
