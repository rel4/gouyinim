package com.gouyin.im.utils;

import android.content.Intent;

import com.gouyin.im.AppConstant;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.center.widget.DefaultDynamicSendActivity;
import com.gouyin.im.center.widget.DynamicSendActivity;
import com.gouyin.im.center.widget.RedpacketDynaimcActivity;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.im.widget.AppConversationActivity;
import com.gouyin.im.login.widget.LoginMainActivity;
import com.gouyin.im.main.widget.DynamicDatailsActivity;
import com.gouyin.im.main.widget.RedpacketAcitivity;
import com.gouyin.im.main.widget.UserInfoActivity;
import com.gouyin.im.my.widget.AppointmentActivity;
import com.gouyin.im.my.widget.CertificationActivity;
import com.gouyin.im.my.widget.GetMoneyActivity;
import com.gouyin.im.my.widget.MyOrderActivity;
import com.gouyin.im.my.widget.RZFirstActivity;
import com.gouyin.im.my.widget.RZSecondActivity;
import com.gouyin.im.my.widget.RefundActivity;
import com.gouyin.im.my.widget.SettingActivity;
import com.gouyin.im.my.widget.WithdRawDepositActivity;
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
     * 开启一个activity
     *
     * @param clz
     */
    public static void startActivity(Class clz) {
        Intent intent = new Intent(ConfigUtils.getInstance().getActivityContext(), clz);
        ConfigUtils.getInstance().getActivityContext().startActivity(intent);
    }

    /**
     * 开启一个activity
     *
     * @param intent
     */
    public static void startActivity(Intent intent) {
        ConfigUtils.getInstance().getActivityContext().startActivity(intent);
    }

    /**
     * 进入用户信息列表
     */
    public static void startUserInfoActivity(String userId) {
        Intent intent = new Intent(ConfigUtils.getInstance().getApplicationContext(), UserInfoActivity.class);
        LogUtils.e(TAG, "userid : " + userId);
        intent.putExtra(AppConstant.USER_ID, userId);
        startActivity(intent);
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
        startActivity(intent);
    }

    /**
     * 开启动态页面
     */
    public static void startDynamicSendActivity() {
        startActivity(DynamicSendActivity.class);
    }

    /**
     * 普通动态发布
     *
     * @param pics
     */
    public static void startDefaultDynamicSendActivity(ArrayList pics, DynamicSendActivity.DynamicType type) {
        if (UserInfoUtils.isLogin()) {
            Intent intent = new Intent(ConfigUtils.getInstance().getActivityContext(), DefaultDynamicSendActivity.class);
            intent.putExtra("type", type.getValue());
            intent.putExtra("data", pics);
            startActivity(intent);
        } else
            RxBus.getInstance().send(Events.EventEnum.LOGIN, null);

    }

    public static void startPhonePicActivity() {
        startActivity(PhonePicActivity.class);
    }

    public static void startShowImageActivity(List<String> childList) {
        Intent mIntent = new Intent(ConfigUtils.getInstance().getActivityContext(),
                ShowImageActivity.class);
        mIntent.putStringArrayListExtra("data",
                (ArrayList<String>) childList);
        startActivity(mIntent);
    }

    /**
     * 开启登录页面
     */
    public static void startLoginMainActivity() {
        startActivity(LoginMainActivity.class);
    }

    /**
     * 开启聊天页面
     *
     * @param userId
     * @param name
     * @param avatar
     */
    public static void startAppConversationActivity(String userId, String name, String avatar) {
        if (!UserInfoUtils.isLogin()) {
            RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
            return;
        }
        Intent intent = new Intent(ConfigUtils.getInstance().getActivityContext(), AppConversationActivity.class);
        intent.putExtra("id", userId);
        intent.putExtra("name", name);
        intent.putExtra("avater", avatar);
        startActivity(intent);

    }

    /**
     * 打赏
     *
     * @param userId
     * @param name
     * @param avater
     */
    public static void startRedpacketActivity(String userId, String name, String avater) {
        if (!UserInfoUtils.isLogin()) {
            RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
            return;
        }
        Intent intent = new Intent(ConfigUtils.getInstance().getActivityContext(), RedpacketAcitivity.class);
        intent.putExtra("id", userId);
        intent.putExtra("name", name);
        intent.putExtra("avater", avater);
        startActivity(intent);
    }

    /**
     * 订单
     */
    public static void startMyOrderActivity() {
        startActivity(MyOrderActivity.class);
    }

    /**
     * 认证
     */
    public static void startCertification() {
        startActivity(CertificationActivity.class);
    }

    /**
     * 认证第一步
     */
    public static void startRZFirstActivity() {
        startActivity(RZFirstActivity.class);
    }

    /**
     * 认证第二步
     */
    public static void startRZSecondActivity() {
        startActivity(RZSecondActivity.class);
    }

    /**
     * 预约
     */
    public static void startAppointmentActivity() {
        startActivity(AppointmentActivity.class);
    }

    /**
     * 退款
     */
    public static void startRefundActivity() {
        startActivity(RefundActivity.class);
    }

    /**
     * 设置
     */
    public static void startSettingActivity() {
        startActivity(SettingActivity.class);
    }

    /**
     * 密码修改
     */
    public static void startChangepwdActivity() {
        startActivity(ChangepwdActivity.class);
    }

    /**
     * 体现
     */
    public static void startWithdRawDepositActivity() {
        startActivity(WithdRawDepositActivity.class);
    }

    /**
     * 提现
     */
    public static void startGetMoneyActivity() {
        startActivity(GetMoneyActivity.class);
    }

    /**
     * 红包动态
     */
    public static void startRedpacketDynaimcActivity() {
        startActivity(RedpacketDynaimcActivity.class);
    }
}
