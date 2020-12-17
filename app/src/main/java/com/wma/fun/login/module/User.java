package com.wma.fun.login.module;


import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.wma.fun.BR;
import com.wma.fun.R;
import com.wma.library.base.BaseModule;
import com.wma.library.utils.http.HttpCallbackListener;
import com.wma.library.utils.http.Request;

import org.xutils.http.HttpMethod;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class User extends BaseModule {

    private final String URL_REGISTER = HOST + "/user/register";

    private final String URL_LOGIN = HOST + "/user/login";

    private final String URL_RESET_PASSWORD = HOST + "/user/resetPassword";

    private final String URL_UPDATE_USER = HOST + "/user/updateUser";

    private final String URL_GET_USER_BY_ID = HOST + "/user/getUserById";


    private String id;
// ----------------------------------------------------- 账户信息开始;
    /**
     * 用户账户
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户Token
     */
    private String token;

// ----------------------------------------------------- 账户信息结束;


// ----------------------------------------------------- 用户基本信息开始;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户电话号码
     */
    private String userPhone;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户头像
     */
    private String headImage;

    /**
     * 背景墙
     */
    private String bgWall;

    /**
     * 性别，0：男，1：女，2：其他
     */
    private Integer sex;

    /**
     * 国家
     */
    private Long birthday;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区域
     */
    private String area;

    /**
     * 登录时间
     */
    private Long loginTime;


// ----------------------------------------------------- 用户基本信息结束;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getBgWall() {
        return bgWall;
    }

    public void setBgWall(String bgWall) {
        this.bgWall = bgWall;
    }

    @Bindable
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
        notifyPropertyChanged(com.wma.fun.BR.sex);
    }

    @Bindable
    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(com.wma.fun.BR.birthday);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public void register(HttpCallbackListener callback, String account, String password, String email, String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);
        params.put("userPhone", phone);
        params.put("userEmail", email);
        Request request = new Request(HttpMethod.POST, URL_REGISTER, params, Request.FROM_MYSELF);
        mHttpUtils.request(request, callback);
    }

    public void login(HttpCallbackListener callback, String account, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);
        Request request = new Request(HttpMethod.POST, URL_LOGIN, params, Request.FROM_MYSELF);
        mHttpUtils.request(request, callback);
    }

    public void resetPassword(HttpCallbackListener callback, String account, String userName, String userPhone, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("userName", userName);
        params.put("userPhone", userPhone);
        params.put("password", password);
        Request request = new Request(HttpMethod.POST, URL_RESET_PASSWORD, params, Request.FROM_MYSELF);
        mHttpUtils.request(request, callback);
    }

    public void updateUser(HttpCallbackListener callback, User user) {
        Map<String, String> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("userName", user.getUserName());
        params.put("userPhone", user.getUserPhone());
        params.put("userEmail", user.getUserEmail());
        params.put("sex", user.getSex() + "");
        params.put("bgWall", user.getBgWall());
        params.put("headImage", user.getHeadImage());
        params.put("birthday", user.getBirthday() + "");
        params.put("province", user.getProvince());
        params.put("city", user.getCity());
        params.put("area", user.getArea());
        Request request = new Request(HttpMethod.POST, URL_UPDATE_USER, params, Request.FROM_MYSELF);
        mHttpUtils.request(request, callback);
    }

    public void getUserById(HttpCallbackListener callback, String id) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", id);
        Request request = new Request(HttpMethod.GET, URL_GET_USER_BY_ID, params, Request.FROM_MYSELF);
        mHttpUtils.request(request, callback);
    }

    public String getSexStr(Integer sex) {
        if (sex == null || "null".equalsIgnoreCase(String.valueOf(sex))) {
            return "";
        }
        return sex == 0 ? "男性" : "女性";
    }

    @BindingAdapter("setSexBtn")
    public static void setSexBtn(RadioGroup group, Integer sex) {
        if (sex == null || "null".equalsIgnoreCase(String.valueOf(sex))) {
            return ;
        }
        if(sex == 0){
            group.check(R.id.rb_male);
        }
        if(sex == 1){
            group.check(R.id.rb_female);
        }
    }

}
