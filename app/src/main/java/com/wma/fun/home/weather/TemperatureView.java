package com.wma.fun.home.weather;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.wma.fun.R;
import com.wma.library.log.Logger;
import com.wma.library.utils.DPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/10/29 0029
 */
public class TemperatureView extends View {
    final String TAG = TemperatureView.class.getSimpleName();
    private Context mContext;

    // -------------------------- 背景
    private Paint mBgPaint;// 背景，刻度画笔
    private int mBgStrokeWidth = 1;// 背景笔宽

    private int mCalibrationStrokeWidth = 1;//刻度宽度
    private int mCalibrationStrokeLength = 5;//刻度长度
    private int mMaxAndMinTemperature = 40;// 最大温度和最小温度
    // -------------------------- 背景


    // -------------------------- 单位
    private Paint mUnitPaint;
    private String mUnitStr = "℃";// 单位字符串
    private Rect mUnitBound;
    // -------------------------- 单位


    int mThermometerWidth; // 温度计宽度
    RectF mThermometerRect;// 温度计矩形


    float mTotalCalibrationLength; // 刻度尺总长度
    float mMidY;// 0刻度Y坐标
    float mCurCircleRadius = 8;
    RectF mCurRect;//当前刻度指示器
    private float mCurY = -1;// 当前温度数
    private float mMaxY = 0;
    private float mMinY = 0;
    private float mMaxTemp = -1000;
    private float mMinTemp = -1000;
    private Paint mCurTemPaint;// 当前刻度

    private int mWidth, mHeight;// 控件的宽高

    public TemperatureView(Context context) {
        this(context, null);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mThermometerWidth = DPUtils.dip2px(mContext, 40);
        mThermometerRect = new RectF();
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeWidth(mBgStrokeWidth);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
//        mBgPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));

        mUnitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnitPaint.setColor(Color.GRAY);
        mUnitPaint.setStrokeCap(Paint.Cap.ROUND);
        mUnitPaint.setTextSize(25);
        mUnitPaint.setTextAlign(Paint.Align.CENTER);
        mUnitBound = new Rect();
        mUnitPaint.getTextBounds(mUnitStr, 0, mUnitStr.length(), mUnitBound);
//        mUnitPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));

        mCurTemPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCurTemPaint.setStyle(Paint.Style.FILL);
        mCurTemPaint.setStrokeCap(Paint.Cap.ROUND);
        mCurTemPaint.setStrokeWidth(mBgStrokeWidth);
        mCurTemPaint.setColor(Color.RED);
        mCurTemPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        mCurRect = new RectF();


    }

    List<Calibration> mCalibrationList = new ArrayList<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mWidth > mHeight) { // 横着摆放，progressWidth 应该等于控件高度
            mHeight = mThermometerWidth;
        } else {// 竖着摆放，progressWidth 应该等于控件宽度
            mWidth = mThermometerWidth;
        }
        setMeasuredDimension(mWidth, mHeight);
        mThermometerRect.set(getPaddingStart(), getPaddingTop(), mWidth - getPaddingEnd(), mHeight - getPaddingBottom());
        generateCalibration(mMaxTemp, mMinTemp, 0);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mCurY == -1) {
            mCurY = mHeight - getPaddingBottom() - mWidth / 2;
        }
        Calibration calibration = getCalibration(0);
        if (mMaxY == 0) {
            mMaxY = calibration.getEndP().y;
        }
        if (mMinY == 0) {
            mMinY = calibration.getEndP().y;
        }
    }

    /**
     * 生成刻度集合
     */

    private void generateCalibration(float max, float min, float curTem) {
        mMidY = (mThermometerRect.bottom - mThermometerRect.top) / 2;
        float topLength = mMidY - (mThermometerWidth / 2 + mThermometerRect.top);
        mCalibrationList.clear();
        float length = topLength / mMaxAndMinTemperature;
        for (int i = 0; i <= mMaxAndMinTemperature; i++) {
            PointF startP = new PointF(mThermometerRect.left, mMidY - length * i);
            PointF endP;
            if (i % 10 == 0) {
                endP = new PointF(mThermometerRect.left + mCalibrationStrokeLength * 2, mMidY - length * i);

            } else {
                endP = new PointF(mThermometerRect.left + mCalibrationStrokeLength, mMidY - length * i);
            }
            Calibration c = new Calibration(startP, endP, mCalibrationStrokeWidth, i);
            c.setCur(c.getNumber() == curTem);
            c.setMax(c.getNumber() == max);
            c.setMin(c.getNumber() == min);
            if (c.isMin()) {
                c.setNumberStr("Min " + c.getNumber());
                PointF endP1 = c.getEndP();
                endP1.x = mThermometerRect.left + mCalibrationStrokeLength;
                c.setEndP(endP1);
            } else if (c.isMax()) {
                c.setNumberStr("Max " + c.getNumber());
                PointF endP1 = c.getEndP();
                endP1.x = mThermometerRect.left + mCalibrationStrokeLength;
                c.setEndP(endP1);
            }
            mCalibrationList.add(c);
        }

        float bottomLength = mThermometerRect.bottom - mMidY - mThermometerWidth / 2;
        length = bottomLength / mMaxAndMinTemperature;
        for (int i = 0; i <= mMaxAndMinTemperature; i++) {
            PointF startP = new PointF(mThermometerRect.left, mMidY + length * i);
            PointF endP;
            if (i % 10 == 0) {
                endP = new PointF(mThermometerRect.left + mCalibrationStrokeLength * 2, mMidY + length * i);
            } else {
                endP = new PointF(mThermometerRect.left + mCalibrationStrokeLength, mMidY + length * i);
            }

            Calibration c = new Calibration(startP, endP, mCalibrationStrokeWidth, i * -1);
            c.setCur(c.getNumber() == curTem);
            c.setMax(c.getNumber() == max);
            c.setMin(c.getNumber() == min);
            if (c.isMin()) {
                c.setNumberStr("Min " + c.getNumber());
                PointF endP1 = c.getEndP();
                endP1.x = mThermometerRect.left + mCalibrationStrokeLength;
                c.setEndP(endP1);
            } else if (c.isMax()) {
                c.setNumberStr("Max " + c.getNumber());
                PointF endP1 = c.getEndP();
                endP1.x = mThermometerRect.left + mCalibrationStrokeLength;
                c.setEndP(endP1);
            }
            mCalibrationList.add(c);
        }
        mTotalCalibrationLength = bottomLength + topLength;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LinearGradient mLinearShader = new LinearGradient(mThermometerRect.left, mThermometerRect.bottom, mThermometerRect.left, mThermometerRect.top, ContextCompat.getColor(mContext, R.color.colorCold), ContextCompat.getColor(mContext, R.color.colorHot), Shader.TileMode.REPEAT);
        mBgPaint.setShader(mLinearShader);
        canvas.drawRoundRect(mThermometerRect, 50, 50, mBgPaint);// 画温度计的外框


        drawCalibration(canvas);

        canvas.drawText(mUnitStr, mWidth - getPaddingEnd() - (mUnitBound.right - mUnitBound.left), getPaddingTop() + mWidth / 2, mUnitPaint);// 画单位
        canvas.drawCircle(mWidth / 2 + mCurCircleRadius * 2, mHeight - getPaddingBottom() - mWidth / 2 + mCurCircleRadius, mCurCircleRadius, mCurTemPaint);
        canvas.drawRoundRect(mWidth / 2 + mCurCircleRadius * 2 - 2, mCurY, mWidth / 2 + mCurCircleRadius * 2 + 2, mHeight - getPaddingBottom() - mWidth / 2 + mCurCircleRadius, 50, 50, mCurTemPaint);

    }

    /**
     * 画刻度
     * @param canvas
     */
    private void drawCalibration(Canvas canvas) {
        for (int i = 0; i < mCalibrationList.size(); i++) {// 画刻度
            Calibration calibration = mCalibrationList.get(i);
            if(!calibration.isMin() && !calibration.isMax()){
                canvas.drawLine(calibration.getStartP().x, calibration.getStartP().y, calibration.getEndP().x, calibration.getEndP().y, mBgPaint);
            }
            if(calibration.isMin()){
                Rect numberBound = new Rect();
                String numberStr = calibration.getNumberStr();
                mBgPaint.getTextBounds(numberStr, 0, numberStr.length(), numberBound);
                canvas.drawText(numberStr, (int) (calibration.getEndP().x + 5), (int) (mMinY + (numberBound.bottom - numberBound.top) / 2), mBgPaint);
            }
            if(calibration.isMax()){
                Rect numberBound = new Rect();
                String numberStr = calibration.getNumberStr();
                mBgPaint.getTextBounds(numberStr, 0, numberStr.length(), numberBound);
                canvas.drawText(numberStr, (int) (calibration.getEndP().x + 5), (int) (mMaxY + (numberBound.bottom - numberBound.top) / 2), mBgPaint);
            }
            if (calibration.getNumber() % 10 == 0 && !calibration.isMax() && !calibration.isMin()) {
                Rect numberBound = new Rect();
                String numberStr = calibration.getNumberStr();
                mBgPaint.getTextBounds(numberStr, 0, numberStr.length(), numberBound);
                canvas.drawText(numberStr, (int) (calibration.getEndP().x + 5), (int) (calibration.getEndP().y + (numberBound.bottom - numberBound.top) / 2), mBgPaint);
            }
        }
    }

    /**
     * 设置当前温度
     *
     * @param temperature
     */
    public void setTemperature(float max, float min, float temperature) {
        mMaxTemp = max;
        mMinTemp = min;
        generateCalibration(max, min, temperature);
        Calibration curCalibration = getCalibration(temperature);
        if(curCalibration != null){
            float start = mCurY;
            ValueAnimator anim = ValueAnimator.ofFloat(start, curCalibration.getStartP().y);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    mCurY = animatedValue;
                    postInvalidate();
                }
            });
            anim.setDuration(1000);
            anim.start();
        }


        Calibration calibration0 = getCalibration(0);
        Calibration maxCalibration = getCalibration(max);
        if(maxCalibration != null){
            ValueAnimator animMax = ValueAnimator.ofFloat(calibration0.getEndP().y, maxCalibration.getEndP().y);
            animMax.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    mMaxY = animatedValue;
                    postInvalidate();
                }
            });
            animMax.setDuration(1000);
            animMax.start();
        }


        Calibration minCalibration = getCalibration(min);
        if(minCalibration != null){
            ValueAnimator animMin = ValueAnimator.ofFloat(calibration0.getEndP().y, minCalibration.getEndP().y);
            Logger.d(TAG, "setTemperature: " + minCalibration.getEndP().y);
            animMin.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    mMinY = animatedValue ;
                    postInvalidate();
                }
            });
            animMin.setDuration(1000);
            animMin.start();
        }
    }


    /**
     * 获取对应温度的刻度对象
     *
     * @param number
     * @return
     */
    private Calibration getCalibration(float number) {
        for (int i = 0; i < mCalibrationList.size(); i++) {
            Calibration calibration = mCalibrationList.get(i);
            if (calibration.getNumber() == number) {
                return calibration;
            }
        }
        return null;
    }
}

