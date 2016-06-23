package com.gouyin.im.utils;

import android.content.Intent;

import com.gouyin.im.AppConstant;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.center.widget.DefaultDynamicSendActivity;
import com.gouyin.im.center.widget.DynamicSendActivity;
import com.gouyin.im.login.widget.LoginMainActivity;
import com.gouyin.im.main.widget.DynamicDatailsActivity;
import com.gouyin.im.main.widget.UserInfoActivity;
import com.gouyin.im.widget.image.PhonePicActivity;
import com.gouyin.im.widget.image.ShowImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jb on 2016/6/20.
 */
public class ActivityUtils {
    private static String TAG = "ActivityUtils";

    /**
     * 进入用户信息列表
     */
    public static void startUserInfoActivity(String userId) {
        Intent intent = new Intent(ConfigUtils.getInstance().getApplicationContext(), UserInfoActivity.class);
        LogUtils.e(TAG, "userid : " + userId);
        intent.putExtra(AppConstant.USER_ID, userId);
        UIUtils.startActivity(intent);
    }

    /**
     * 动态详情页面跳转
     *
     * @param bean
     */
    public static void startDynamicDatailsActivity(UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean) {
        if (bean == null)
            return;
        Intent intent = new Intent(ConfigUtils.getInstance().getApplicationContext(), DynamicDatailsActivity.class);
        intent.putExtra(AppConstant.DYNAMIC_DATAILS, bean);
        LogUtils.d(TAG, bean.getId());
        UIUtils.startActivity(intent);
    }

    /**
     * 开启动态页面
     */
    public static void startDynamicSendActivity() {
        UIUtils.startActivity(DynamicSendActivity.class);
    }

    /**
     * 普通动态发布
     */
    public static void startDefaultDynamicSendActivity() {
        UIUtils.startActivity(DefaultDynamicSendActivity.class);
    }

    public static void startPhonePicActivity() {
        UIUtils.startActivity(PhonePicActivity.class);
    }

    public static void startShowImageActivity(List<String> childList) {
        Intent mIntent = new Intent(ConfigUtils.getInstance().getActivityContext(),
                ShowImageActivity.class);
        mIntent.putStringArrayListExtra("data",
                (ArrayList<String>) childList);
        UIUtils.startActivity(mIntent);
    }

    public static void startLoginMainActivity() {
        UIUtils.startActivity(LoginMainActivity.class);
    }
}
