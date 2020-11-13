package com.wma.fun.home.news;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wma.fun.R;
import com.wma.fun.databinding.ActivityNewsListBinding;
import com.wma.library.base.BaseListActivity;
import com.wma.library.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public class NewsListActivity extends BaseListActivity<NewsModule, ActivityNewsListBinding> {

    @Override
    public BaseRecyclerAdapter getAdapter() {
        return new NewsListAdapter(mList, this);
    }

    @Override
    public void loadData() {
        new NewsModule().loadNews(this,"top");
    }


    @Override
    public String getTitleStr() {
        return "列表";
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected View getRefreshView() {
        return mBinding.layoutRefresh.getRoot();
    }

    @Override
    public void handleBySuccess(List<NewsModule> result) {
        super.handleBySuccess(result);
        addList(result);
    }
}
