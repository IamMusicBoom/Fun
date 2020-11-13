package com.wma.fun.home.news;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.wma.library.log.Logger;
import com.wma.library.utils.DPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by wma
 * on 2020/11/4 0004
 */
@Deprecated
public class KindView extends View {
    final String TAG = KindView.class.getSimpleName();
    private String[] kindKeyStrS = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private String[] kindValueStrS = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
    private Map<String, String> mKindMap = new HashMap<>();
    private Context mContext;
    private int mWidth, mHeight;
    private int mArrowRadius;

    private int mBigRadius;

    private int mBtnRadius;

    private Paint mPaint;
    private Paint mTextPaint;
    private Rect mTextBounds;
    private int mStrokeWidth = 1;

    private List<KindModule> mKindList = new ArrayList<>();

    public KindView(Context context) {
        this(context, null);
    }

    public KindView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KindView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mBtnRadius = DPUtils.dip2px(mContext, 25);
        mArrowRadius = DPUtils.dip2px(mContext, 30);
        mBigRadius = DPUtils.dip2px(mContext, 100);
        for (int i = 0; i < kindKeyStrS.length; i++) {
            mKindMap.put(kindKeyStrS[i], kindValueStrS[i]);
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.CYAN);
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(30);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(mStrokeWidth);
        mTextBounds = new Rect();

        for (String kindName : mKindMap.keySet()) {
            mKindList.add(new KindModule(null, kindName, mKindMap.get(kindName)));
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        mBigRadius = DPUtils.dip2px(mContext, 80);
        mArrowRadius = mBigRadius / 2;
        maxWidth = maxHeight = Math.min(maxWidth, maxHeight);
        mWidth = mHeight = mBigRadius * 2;
        mWidth = mHeight = Math.min(maxWidth, mWidth);
        setMeasuredDimension(mWidth, mHeight);
        mBigRadius = mWidth / 2;
        mArrowRadius = mBigRadius / 2;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mArrowRadius, mPaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPaint);
        for (int i = 0; i < mKindList.size(); i++) {
            KindModule kindModule = mKindList.get(i);
            String kindName = kindModule.getKey();
            mTextPaint.getTextBounds(kindName, 0, kindName.length(), mTextBounds);
            int height = mTextBounds.bottom - mTextBounds.top;
            int width = mTextBounds.right - mTextBounds.left;
            float x = mWidth / 2;
            float y = mHeight / 2f - mBtnRadius - (mBigRadius - mBtnRadius) / 2f;
            canvas.drawText(kindName, x, y, mTextPaint);
            if(kindModule.getRect() == null){
                RectF rectF = new RectF(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
                Logger.d(TAG, "onDraw: name = " + kindName + " rect = " + rectF);
                kindModule.setRect(rectF);
            }
            canvas.rotate(360 / mKindMap.size(), mWidth / 2, mHeight / 2);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                Logger.d(TAG, "onTouchEvent: x = " + x + " y = " + y + " mKindList = " + mKindList.size());
                for (int i = 0; i < mKindList.size(); i++) {
                    KindModule kindModule = mKindList.get(i);
                    RectF rect = kindModule.getRect();
                    if (rect.contains(x, y)) {
                        Logger.d(TAG, "onTouchEvent: " + kindModule.getKey());
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
