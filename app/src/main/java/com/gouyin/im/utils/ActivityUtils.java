package com.gouyin.im.utils;

import android.content.Intent;

import com.gouyin.im.AppConstant;
import com.gouyin.im.main.widget.UserInfoActivity;

/**
 * Created by jb on 2016/6/20.
 */
public class ActivityUtils {
    /**
     * 进入用户信息列表
     */
    public static void startUserInfoActivity(String userId) {
        Intent intent = new Intent(ConfigUtils.getInstance().getApplicationContext(), UserInfoActivity.class);
        LogUtils.e("ActivityUtils", "userid : " + userId);
        intent.putExtra(AppConstant.USER_ID, userId);
        UIUtils.startActivity(intent);
    }
}
