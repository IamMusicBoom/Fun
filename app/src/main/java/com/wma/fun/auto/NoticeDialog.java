package com.wma.fun.auto;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.wma.fun.R;


/**
 * create by wma
 * on 2020/9/30 0030
 */
public class NoticeDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = NoticeDialog.class.getSimpleName();
    private final static String TITLE = "TITLE";
    private final static String MESSAGE = "MESSAGE";
    private final static String TYPE = "TYPE";

    TextView mTitle, mMessage, mCancelBtn, mSureBtn;
    private String mCurType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_notice, container, false);
        mTitle = view.findViewById(R.id.dialog_title);
        mMessage = view.findViewById(R.id.dialog_message);
        mSureBtn = view.findViewById(R.id.dialog_btn_sure);
        mCancelBtn = view.findViewById(R.id.dialog_btn_cancel);
        mCancelBtn.setOnClickListener(this);
        mSureBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(false);
        Bundle arguments = getArguments();
        mTitle.setText(TextUtils.isEmpty(arguments.getString(TITLE)) ? "标题" : arguments.getString(TITLE));
        mMessage.setText(TextUtils.isEmpty(arguments.getString(MESSAGE)) ? "信息" : arguments.getString(MESSAGE));
        mCurType = arguments.getString(TYPE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {

        if (v == mSureBtn) {
            OpenAccessibilitySettingHelper.goSettingActivity(getActivity());
            dismiss();
            Intent intent = new Intent(getActivity(), AutoService.class);
            intent.putExtra(AutoService.TYPE, mCurType);
            getActivity().startService(intent);
            getActivity().finish();
        } else if (v == mCancelBtn) {
            dismiss();
            getActivity().finish();
        }
    }


    public static NoticeDialog newInstanse(String title, String message, String type) {
        NoticeDialog dialog = new NoticeDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(MESSAGE, message);
        bundle.putString(TYPE, type);
        dialog.setArguments(bundle);
        return dialog;
    }
}
