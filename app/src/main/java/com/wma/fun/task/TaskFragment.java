package com.wma.fun.task;

import com.wma.fun.R;
import com.wma.fun.databinding.FragmentHomeBinding;
import com.wma.library.base.BaseFragment;

/**
 * create by wma
 * on 2020/10/22 0022
 */
public class TaskFragment extends BaseFragment<FragmentHomeBinding> {
    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task;
    }
}
