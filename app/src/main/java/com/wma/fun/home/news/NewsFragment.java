package com.wma.fun.home.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wma.fun.R;
import com.wma.fun.data.UserSP;
import com.wma.fun.databinding.FragmentNewsBinding;
import com.wma.fun.databinding.ViewNewsBinding;
import com.wma.library.base.BaseLoadFragment;
import com.wma.library.base.BasePagerAdapter;
import com.wma.library.base.BasePagerTransformer;
import com.wma.library.base.BaseWebViewActivity;
import com.wma.library.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/11/4 0004
 */
public class NewsFragment extends BaseLoadFragment<NewsModule, FragmentNewsBinding> {

    List<View> mViewList;

    BasePagerAdapter mAdapter;



    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mViewList = new ArrayList<>();
        autoRefresh();
    }


    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return mBinding.smartRefreshLayout;
    }

    @Override
    protected void loadData() {
        new NewsModule().loadNews(this, UserSP.getFavorite());
    }

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void handleBySuccess(List<NewsModule> result) {
        super.handleBySuccess(result);
        Logger.d(TAG, "success: " + result.size());
        if (result != null && result.size() >= 3) {
            for (int i = 0; i < 4; i++) {
                if(i<3){
                    final NewsModule newsModule = result.get(i);
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.view_news,null);
                    ViewNewsBinding binding = DataBindingUtil.bind(view);
                    binding.setNewsModule(newsModule);
                    mViewList.add(binding.getRoot());
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
                            intent.putExtra(BaseWebViewActivity.KEY_TITLE,newsModule.getTitle());
                            intent.putExtra(BaseWebViewActivity.KEY_URL,newsModule.getUrl());
                            startActivity(intent);
                        }
                    });
                }else{
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.view_show_more_news,null);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),NewsListActivity.class);
                            startActivity(intent);
                        }
                    });
                    mViewList.add(view);
                }

            }
            mAdapter = new BasePagerAdapter(mViewList);
            mBinding.newsPager.setPageTransformer(true,new BasePagerTransformer());
            mBinding.newsPager.setAdapter(mAdapter);
        }

    }
}
