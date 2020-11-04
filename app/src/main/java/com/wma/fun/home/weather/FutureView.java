package com.wma.fun.home.weather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.wma.fun.R;
import com.wma.fun.home.weather.module.FutureTemperature;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/11/3 0003
 */
public class FutureView extends View {
    private Paint mBgPaint, mTextPaint, mMaxPaint, mMinPaint;
    private int mCoordinateStrokeWidth = 2;

    private Context mContext;

    private int mWidth, mHeight;

    private String mUnitStr = "℃", mMaxTemperatureStr = "最高气温", mMinTemperatureStr = "最低气温";

    private RectF mBgRect;

    private Path mMaxPath, mMinPath;


    private List<Calibration> mTemperatureCalibrations;

    private List<FutureTemperature> mFutureList;

    public FutureView(Context context) {
        this(context, null);
    }

    public FutureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FutureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeWidth(mCoordinateStrokeWidth);
        mBgPaint.setColor(Color.GRAY);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);


        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setStrokeWidth(mCoordinateStrokeWidth);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setTextSize(15);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);


        mMaxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaxPaint.setStyle(Paint.Style.STROKE);
        mMaxPaint.setStrokeWidth(mCoordinateStrokeWidth);
        mMaxPaint.setColor(ContextCompat.getColor(mContext, R.color.colorHot));
        mMaxPaint.setTextSize(15);
        mMaxPaint.setStrokeCap(Paint.Cap.ROUND);


        mMinPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMinPaint.setStyle(Paint.Style.STROKE);
        mMinPaint.setStrokeWidth(mCoordinateStrokeWidth);
        mMinPaint.setColor(ContextCompat.getColor(mContext, R.color.colorCold));
        mMinPaint.setTextSize(15);
        mMinPaint.setStrokeCap(Paint.Cap.ROUND);

        mBgRect = new RectF();

        mTemperatureCalibrations = new ArrayList<>();

        mMaxPath = new Path();
        mMinPath = new Path();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        Rect bounds = new Rect();
        mTextPaint.getTextBounds("-40", 0, "-40".length(), bounds);

        setMeasuredDimension(mWidth, mHeight);
        mBgRect.set(getPaddingStart() + (bounds.right - bounds.left) + 20, getPaddingTop(), mWidth - getPaddingEnd(), mHeight - getPaddingBottom());
        generateTemperatureCalibration();
    }

    /**
     * 生成温度刻度
     */
    private void generateTemperatureCalibration() {
        mTemperatureCalibrations.clear();
        float midY = (mBgRect.bottom - mBgRect.top) / 2f + getPaddingTop();
        float length = (mBgRect.bottom - mBgRect.top) / 2f / 40;
        PointF startP, endP;
        for (int i = 0; i <= 40; i++) {
            startP = new PointF(mBgRect.left, midY + length * i);
            if (i % 10 == 0) {
                endP = new PointF(mBgRect.left + 20, midY + length * i);
            } else {
                endP = new PointF(mBgRect.left + 10, midY + length * i);
            }
            Calibration c = new Calibration(startP, endP, mCoordinateStrokeWidth, i * -1);
            mTemperatureCalibrations.add(c);
        }

        for (int i = 1; i <= 40; i++) {
            startP = new PointF(mBgRect.left, midY - length * i);
            if (i % 10 == 0) {
                endP = new PointF(mBgRect.left + 20, midY - length * i);
            } else {
                endP = new PointF(mBgRect.left + 10, midY - length * i);
            }
            Calibration c = new Calibration(startP, endP, mCoordinateStrokeWidth, i);
            mTemperatureCalibrations.add(c);
        }


    }

    /**
     * 生成时间刻度
     */
    private void generateDateCalibration() {
        float everyLength = (mBgRect.right - mBgRect.left) / mFutureList.size();// 每个刻度之间的间隙
        for (int i = 0; i < mFutureList.size(); i++) {
            FutureTemperature futureTemperature = mFutureList.get(i);
            PointF startPoint = new PointF();
            PointF endPoint = new PointF();
            startPoint.x = mBgRect.left + (i + 1) * everyLength;
            startPoint.y = (mBgRect.bottom - mBgRect.top) / 2 + getPaddingTop();
            endPoint.x = startPoint.x;
            endPoint.y = startPoint.y - 10;
            Calibration calibration = new Calibration(startPoint, endPoint, mCoordinateStrokeWidth, futureTemperature.getDate());
            futureTemperature.setCalibration(calibration);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinateSystemAndTemperature(canvas);
    }

    /**
     * @param canvas
     */
    private void drawCoordinateSystemAndTemperature(Canvas canvas) {
        canvas.drawLine(mBgRect.left, mBgRect.bottom, mBgRect.left, mBgRect.top, mBgPaint);// y轴
        canvas.drawLine(mBgRect.left, (mBgRect.bottom - mBgRect.top) / 2 + getPaddingTop(), mBgRect.right, (mBgRect.bottom - mBgRect.top) / 2 + getPaddingTop(), mBgPaint);// x轴
        for (int i = 0; i < mTemperatureCalibrations.size(); i++) {
            Calibration calibration = mTemperatureCalibrations.get(i);
            canvas.drawLine(calibration.getStartP().x, calibration.getStartP().y, calibration.getEndP().x, calibration.getEndP().y, mBgPaint);
            if (calibration.getNumber() % 10 == 0) {
                Rect bounds = new Rect();
                mTextPaint.getTextBounds(calibration.getNumberStr(), 0, calibration.getNumberStr().length(), bounds);
                int height = bounds.bottom - bounds.top;
                canvas.drawText(calibration.getNumberStr(), calibration.getStartP().x - 20, calibration.getStartP().y + (height / 2f), mTextPaint);
            }
        }
        if (mFutureList != null) {
            mMinPath.reset();
            mMaxPath.reset();
            for (int i = 0; i < mFutureList.size(); i++) {
                FutureTemperature futureTemperature = mFutureList.get(i);
                Calibration calibration = futureTemperature.getCalibration();
                canvas.drawLine(calibration.getStartP().x, calibration.getStartP().y, calibration.getEndP().x, calibration.getEndP().y, mBgPaint);
                Rect bounds = new Rect();
                mTextPaint.getTextBounds(calibration.getNumberStr(), 0, calibration.getNumberStr().length(), bounds);
                int height = bounds.bottom - bounds.top;
                int width = bounds.right - bounds.left;
                canvas.drawText(calibration.getNumberStr(), calibration.getStartP().x - width / 2f, calibration.getStartP().y + height + 10, mTextPaint);
                if (i == 0) {
                    mMinPath.moveTo(futureTemperature.getMinPoint().x, futureTemperature.getMinPoint().y);
                    mMaxPath.moveTo(futureTemperature.getMaxPoint().x, futureTemperature.getMaxPoint().y);
                } else {
                    mMinPath.lineTo(futureTemperature.getMinPoint().x, futureTemperature.getMinPoint().y);
                    mMaxPath.lineTo(futureTemperature.getMaxPoint().x, futureTemperature.getMaxPoint().y);
                }
            }
            canvas.drawPath(mMinPath, mMinPaint);
            canvas.drawPath(mMaxPath, mMaxPaint);
        }

        Rect maxBounds = new Rect();
        mMaxPaint.getTextBounds(mMaxTemperatureStr, 0, mMaxTemperatureStr.length(), maxBounds);
        int maxHeight = maxBounds.bottom - maxBounds.top;
        int maxWidth = maxBounds.right - maxBounds.left;
        canvas.drawText(mMaxTemperatureStr, mBgRect.left + getPaddingEnd() + 10, mBgRect.top + maxHeight + getPaddingTop(), mMaxPaint);
        canvas.drawLine(mBgRect.left + maxWidth + getPaddingEnd() + 20, mBgRect.top + maxHeight + getPaddingTop() - maxHeight / 2f,
                mBgRect.left + maxWidth + getPaddingEnd() + 60, mBgRect.top + maxHeight + getPaddingTop() - maxHeight / 2f, mMaxPaint);

        Rect minBounds = new Rect();
        mMinPaint.getTextBounds(mMinTemperatureStr, 0, mMinTemperatureStr.length(), minBounds);
        int minHeight = minBounds.bottom - minBounds.top;
        int minWidth = minBounds.right - minBounds.left;
        canvas.drawText(mMinTemperatureStr, mBgRect.left + getPaddingEnd() + 10, mBgRect.bottom - minHeight + getPaddingBottom(), mMinPaint);
        canvas.drawLine(mBgRect.left + minWidth + getPaddingEnd() + 20, mBgRect.bottom - minHeight + getPaddingBottom() - minHeight / 2f,
                mBgRect.left + minWidth + getPaddingEnd() + 60, mBgRect.bottom - minHeight + getPaddingBottom() - minHeight / 2f, mMinPaint);

        Rect unitBounds = new Rect();
        mTextPaint.getTextBounds(mUnitStr, 0, mUnitStr.length(), unitBounds);
        int unitWidth = unitBounds.right - unitBounds.left;
        int unitHeight = unitBounds.bottom - unitBounds.top;
        canvas.drawText(mUnitStr, mBgRect.right - unitWidth/2f, mBgRect.top + unitHeight, mTextPaint);

    }


    public void setFutureList(List<FutureTemperature> list) {
        mFutureList = list;
        generateDateCalibration();
        generatePoints();
        postInvalidate();
    }

    private void generatePoints() {
        for (int i = 0; i < mFutureList.size(); i++) {
            FutureTemperature futureTemperature = mFutureList.get(i);
            PointF maxPointF = getTemperaturePoint(futureTemperature.getDate(), futureTemperature.getMaxTemperature());
            PointF minPointF = getTemperaturePoint(futureTemperature.getDate(), futureTemperature.getMinTemperature());
            futureTemperature.setMinPoint(minPointF);
            futureTemperature.setMaxPoint(maxPointF);
        }
    }

    public PointF getTemperaturePoint(String date, String temperature) {
        PointF pointF = new PointF();
        for (int i = 0; i < mFutureList.size(); i++) {
            FutureTemperature futureTemperature = mFutureList.get(i);
            if (futureTemperature.getDate().equals(date)) {
                pointF.x = futureTemperature.getCalibration().getStartP().x;
            }
        }
        for (int i = 0; i < mTemperatureCalibrations.size(); i++) {
            Calibration calibration = mTemperatureCalibrations.get(i);
            if (calibration.getNumberStr().equals(temperature)) {
                pointF.y = calibration.getStartP().y;
            }
        }
        return pointF;
    }
}
