package com.wma.fun.home.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wma.fun.R;
import com.wma.fun.databinding.FragmentNewsListBinding;
import com.wma.library.base.BaseListFragment;
import com.wma.library.base.BaseRecyclerAdapter;
import com.wma.library.base.BaseWebViewActivity;

import java.util.List;

/**
 * create by wma
 * on 2020/11/16 0016
 */
public class NewsListFragment extends BaseListFragment<NewsModule, FragmentNewsListBinding> {
    private String mCurType = "top";

    @Override
    public BaseRecyclerAdapter getAdapter() {
        return new NewsListAdapter(mList, mContext);
    }

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_list;
    }


    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        mCurType = arguments.getString("loadValue");
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<NewsModule>() {
            @Override
            public void onItemClickListener(int position, NewsModule data) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(BaseWebViewActivity.KEY_TITLE, data.getTitle());
                intent.putExtra(BaseWebViewActivity.KEY_URL, data.getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadLazyData() {
        super.loadLazyData();
        new NewsModule().loadNews(this, mCurType);
    }

    @Override
    public void handleBySuccess(List<NewsModule> result) {
        super.handleBySuccess(result);
        addList(result);
    }

    public static NewsListFragment newInstance(String value) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("loadValue", value);
        fragment.setArguments(bundle);
        return fragment;
    }
}
