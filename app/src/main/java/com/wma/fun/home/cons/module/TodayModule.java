package com.wma.fun.home.cons.module;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.databinding.BindingAdapter;

import com.wma.fun.R;
import com.wma.fun.home.cons.ProgressView;
import com.wma.library.log.Logger;
import com.wma.library.utils.ConsUtils;


/**
 * create by wma
 * on 2020/10/28 0028
 */
public class TodayModule extends ConsModule {


    /**
     * date : 20201028
     * name : 天蝎座
     * QFriend : 天蝎座
     * color : 白色
     * datetime : 2020年10月28日
     * health : 70
     * love : 60
     * work : 70
     * money : 60
     * number : 9
     * summary : 今天天蝎座可能会变得有些自卑，对自己的价值不过认可，总会因为别人对自己的看法而动摇自信。这是一个提醒，你内在的不安全感是因为你不知道自己有多好。
     * all : 80
     * resultcode : 200
     * error_code : 0
     */

    private int date;
    private String name;
    private String QFriend;
    private String color;
    private String datetime;
    private String health;
    private String love;
    private String work;
    private String money;
    private int number;
    private String summary;
    private String all;
    private String resultcode;


    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQFriend() {
        return QFriend;
    }

    public void setQFriend(String QFriend) {
        this.QFriend = QFriend;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }





    @BindingAdapter("backgroundRes")
    public static void setBackgroundRes(View view,String cons){
        if(TextUtils.isEmpty(cons)){
            view.setBackgroundColor(Color.BLACK);
        }else{
            if(ConsUtils.B_Y_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_byz);
            }else if(ConsUtils.C_N_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_cnz);
            }else if(ConsUtils.J_N_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_jnz);
            }else if(ConsUtils.J_X_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_jxz);
            }else if(ConsUtils.M_J_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_mjz);
            }else if(ConsUtils.S_P_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_spz);
            }else if(ConsUtils.S_S_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_ssz);
            }else if(ConsUtils.S_Y_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_syz);
            }else if(ConsUtils.S_Z_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_szz);
            }else if(ConsUtils.SH_Z_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_shzz);
            }else if(ConsUtils.T_C_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_tcz);
            }else if(ConsUtils.T_X_Z.equals(cons)){
                view.setBackgroundResource(R.mipmap.bg_txz);
            }
        }
    }

}
