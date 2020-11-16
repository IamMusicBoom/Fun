package com.wma.fun.home.news;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.wma.fun.R;
import com.wma.library.base.BaseModule;
import com.wma.library.log.Logger;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create by wma
 * on 2020/11/4 0004
 */
public class NewsModule extends BaseModule {
    /**
     * top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    public static String[] mName = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    public static String[] mValue = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};


    private final String NEWS_URL = "http://v.juhe.cn/toutiao/index";
    /**
     * uniquekey : 5caf434be0c9165ce432dca9e74aa44a
     * title : 她将弟弟“硬拉”进娱乐圈，如今弟弟世人皆知，姐姐却不温不火
     * date : 2020-11-05 14:23
     * category : 头条
     * author_name : 南城深巷
     * url : https://mini.eastday.com/mobile/201105142339470.html
     * thumbnail_pic_s : https://07imgmini.eastday.com/mobile/20201105/20201105142339_ace67cfd31f557a4060147751b9bfe0a_4_mwpm_03200403.jpg
     * thumbnail_pic_s02 : http://07imgmini.eastday.com/mobile/20201105/20201105142339_ace67cfd31f557a4060147751b9bfe0a_2_mwpm_03200403.jpg
     * thumbnail_pic_s03 : http://07imgmini.eastday.com/mobile/20201105/20201105142339_ace67cfd31f557a4060147751b9bfe0a_1_mwpm_03200403.jpg
     */

    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;

    public void loadNews(Callback.CommonCallback callback, String type) {
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("key", "ddf058f205abc13f4a0bd68661b41f55");
        mCancelable = mHttpUtils.get(NEWS_URL, params, callback);
    }


    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }

    @BindingAdapter("showImage")
    public static void showImage(CardView layout, String imgPath) {
        if (layout.getChildCount() > 0) {
            layout.removeAllViews();
        }
        if (TextUtils.isEmpty(imgPath)) {
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
            ImageView imageView = new ImageView(layout.getContext());
            imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            layout.addView(imageView);
            Glide.with(layout.getContext()).load(imgPath).placeholder(R.mipmap.ic_launcher).into(imageView);
        }
    }
}
