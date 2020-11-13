package com.wma.fun.home.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wma.fun.R;
import com.wma.fun.databinding.ActivityNewsListBinding;
import com.wma.library.base.BaseListActivity;
import com.wma.library.base.BaseRecyclerAdapter;
import com.wma.library.base.BaseWebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public class NewsListActivity extends BaseListActivity<NewsModule, ActivityNewsListBinding> {

    @Override
    protected void loadData() {
        new NewsModule().loadNews(this, "top");
        List<NewsModule> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            NewsModule newsModule = new NewsModule();
            newsModule.setTitle("标题" + i);
            list.add(newsModule);
        }
        addList(list);
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<NewsModule>() {
            @Override
            public void onItemClickListener(int position, NewsModule data) {
                Intent intent = new Intent(NewsListActivity.this, NewsDetailActivity.class);
                intent.putExtra(BaseWebViewActivity.KEY_TITLE, data.getTitle());
                intent.putExtra(BaseWebViewActivity.KEY_URL, data.getUrl());
                startActivity(intent);
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_news_list;
    }

    @Override
    public BaseRecyclerAdapter getAdapter() {
        return new NewsListAdapter(mList, this);
    }

    @Override
    public void handleBySuccess(List<NewsModule> result) {
        super.handleBySuccess(result);
        addList(result);
    }

}
