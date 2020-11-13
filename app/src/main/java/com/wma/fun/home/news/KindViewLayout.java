package com.wma.fun.home.news;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wma.fun.R;
import com.wma.library.utils.DPUtils;

/**
 * create by wma
 * on 2020/11/4 0004
 */
@Deprecated
public class KindViewLayout extends FrameLayout {
    public static final int LEFT_TOP = 0;
    public static final int LEFT_MID = 1;
    public static final int LEFT_BOTTOM = 2;
    public static final int RIGHT_TOP = 3;
    public static final int RIGHT_MID = 4;
    public static final int RIGHT_BOTTOM = 5;
    public static final int MID_TOP = 6;
    public static final int MID_BOTTOM = 7;
    public static final int MID = 8;

    private int mCurMode = MID;

    private int mWidth, mHeight;
    private Button mOkBtn;
    private Context mContext;
    private int mOkBtnWidth, mOkBtnHeight;
    private KindView mKindView;

    public KindViewLayout(Context context) {
        this(context, null);
    }

    public KindViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KindViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mOkBtn = new Button(mContext);
        mOkBtnWidth = DPUtils.dip2px(mContext, 50);
        mOkBtnHeight = DPUtils.dip2px(mContext, 50);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mOkBtnWidth, mOkBtnHeight);
        mOkBtn.setLayoutParams(lp);
        mOkBtn.setPadding(0, 0, 0, 0);
        mOkBtn.setText("Ok");
        mOkBtn.setTextColor(Color.WHITE);
        mOkBtn.setGravity(Gravity.CENTER);
        mOkBtn.setBackgroundResource(R.drawable.bg_new_kind_ok_btn);
        addView(mOkBtn);
        mKindView = new KindView(mContext);
        mKindView.setLayoutParams(new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addView(mKindView);
        setBackgroundColor(Color.parseColor("#55000000"));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mOkBtn.layout(mWidth / 2 - mOkBtn.getMeasuredWidth() / 2, mHeight / 2 - mOkBtn.getMeasuredHeight() / 2, mWidth / 2 + mOkBtn.getMeasuredWidth() / 2, mHeight / 2 + mOkBtn.getMeasuredHeight() / 2);
        mKindView.layout(mWidth / 2 - mKindView.getMeasuredWidth() / 2, mHeight / 2 - mKindView.getMeasuredHeight() / 2, mWidth / 2 + mKindView.getMeasuredWidth() / 2, mHeight / 2 + mKindView.getMeasuredHeight() / 2);
    }
}
