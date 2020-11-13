package com.wma.fun.home.news;

import android.content.Context;

import com.wma.fun.R;
import com.wma.fun.databinding.ViewNewsBinding;
import com.wma.library.base.BaseRecyclerAdapter;
import com.wma.library.base.BaseRecyclerHolder;
import com.wma.library.log.Logger;

import java.util.List;


/**
 * create by wma
 * on 2020/11/10 0010
 */
public class NewsListAdapter extends BaseRecyclerAdapter<NewsModule, ViewNewsBinding> {
    final String TAG = NewsListAdapter.class.getSimpleName();

    public NewsListAdapter(List<NewsModule> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    public void bindData(ViewNewsBinding binding, NewsModule data, BaseRecyclerHolder holder, int position) {
        binding.setNewsModule(data);
    }


    @Override
    public int getLayoutId() {
        return R.layout.view_news;
    }
}
