package com.wma.fun.login;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wma.fun.R;

/**
 * create by wma
 * on 2020/11/23 0023
 */
public class CountdownView extends View {
    private Context mContext;
    private Paint mTextPaint, mCirclePaint;
    private int mRadius = 350;
    private int mTextColor;
    private int mCircleColor;
    private int mTextSize;
    private int mWidth, mHeight;
    private String mTextStr = "0S";
    private int mStrokeWidth = 10;
    private Rect mTextBounds = new Rect();
    private RectF mArcRect = new RectF();
    private float mCurAngle = 0;
    private float mMaxTime = 0;
    private float mCurTime = 0;

    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.CountdownView);
        mTextColor = ta.getColor(R.styleable.CountdownView_CTextColor, Color.LTGRAY);
        mCircleColor = ta.getColor(R.styleable.CountdownView_CCircleColor, Color.LTGRAY);
        mTextSize = ta.getDimensionPixelSize(R.styleable.CountdownView_CTextSize, 30);
        ta.recycle();
        init();
    }

    private void init() {


        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);


        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStrokeWidth(mStrokeWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        mTextPaint.getTextBounds(mTextStr, 0, mTextStr.length(), mTextBounds);
        int textWidth = mTextBounds.right - mTextBounds.left;
        int textHeight = mTextBounds.bottom - mTextBounds.top;
        mRadius = (Math.max(textWidth, textHeight) * 3 + 2 * mStrokeWidth) / 2;
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = wideSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = Math.min(wideSize, 2 * mRadius);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = Math.min(heightSize, 2 * mRadius);
        }
        mWidth = mHeight = Math.max(mWidth, mHeight);
        setMeasuredDimension(mWidth, mHeight);
        mRadius = mWidth / 2 - 2 * mStrokeWidth;
        mArcRect.set(0 + mStrokeWidth, 0 + mStrokeWidth, mWidth - mStrokeWidth, mHeight - mStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mTextStr, mWidth / 2, mHeight / 2 + (mTextBounds.bottom - mTextBounds.top) / 2, mTextPaint);
        canvas.drawArc(mArcRect, -90, mCurAngle, false, mCirclePaint);
    }


    public void setCurTime(int time) {
        this.mCurTime = time;
        this.mCurAngle = mCurTime / mMaxTime * 360f;
        mTextStr = time + "S";
        postInvalidate();
    }

    public void setMaxTime(int maxTime) {
        this.mMaxTime = maxTime;
    }
}
