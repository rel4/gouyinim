package com.gouyin.im.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.gouyin.im.AppConstant;
import com.gouyin.im.bean.TiXinrRecordBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.center.widget.DefaultDynamicSendActivity;
import com.gouyin.im.center.widget.DynamicSendActivity;
import com.gouyin.im.center.widget.PayDynamicRedPackketActivity;
import com.gouyin.im.center.widget.RedpacketDynaimcActivity;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.home.widget.SearchActivity;
import com.gouyin.im.home.widget.SearchReasonActivity;
import com.gouyin.im.login.widget.FindPasswordActivity;
import com.gouyin.im.login.widget.LoginMainActivity;
import com.gouyin.im.main.widget.DynamicAtionActivity;
import com.gouyin.im.main.widget.DynamicDatailsActivity;
import com.gouyin.im.login.widget.FindPasswordNextActivity;
import com.gouyin.im.main.widget.PayAppointmentActivity;
import com.gouyin.im.main.widget.PayAppointmentOrderActivity;
import com.gouyin.im.main.widget.PersonInfoChangeActivity;
import com.gouyin.im.main.widget.RedpacketAcitivity;
import com.gouyin.im.main.widget.DynamicActivity;
import com.gouyin.im.main.widget.UserinfoActivity;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.my.widget.AddCardActivity;
import com.gouyin.im.my.widget.AppointmentActivity;
import com.gouyin.im.my.widget.CertificationActivity;
import com.gouyin.im.my.widget.ChangepwdActivity;
import com.gouyin.im.my.widget.GetMoneyActivity;
import com.gouyin.im.my.widget.MyOrderActivity;
import com.gouyin.im.my.widget.RZFirstActivity;
import com.gouyin.im.my.widget.RZSecondActivity;
import com.gouyin.im.my.widget.RZThidActivity;
import com.gouyin.im.my.widget.RefundActivity;
import com.gouyin.im.my.widget.SettingActivity;
import com.gouyin.im.my.widget.SwitchCardActivity;
import com.gouyin.im.my.widget.TiXianRecordActivity;
import com.gouyin.im.my.widget.UserInfoChangeActivity;
import com.gouyin.im.my.widget.WithdRawDepositActivity;
import com.gouyin.im.widget.image.CropImageMainActivity;
import com.gouyin.im.widget.image.PhonePicActivity;
import com.gouyin.im.widget.image.ShowImageActivity;
import com.gouyin.im.widget.image.photoview.ImagePagerActivity;
import com.gouyin.im.widget.takevideo.TakeVideoActivity;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongyunConfig;
import io.rong.imlib.model.Conversation;

/**
 * Created by jb on 2016/6/20.
 */
public class ActivityUtils {
    private static String TAG = "ActivityUtils";

    private static Context getContext() {
        return ConfigUtils.getInstance().getActivityContext();
    }

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
    public static void startDynamicActivity(String userId) {
        Intent intent = new Intent(ConfigUtils.getInstance().getApplicationContext(), DynamicActivity.class);
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
        if (UserInfoManager.getInstance().isLogin()) {
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
        if (!UserInfoManager.getInstance().isLogin()) {
            RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
            return;
        }
        Uri uri = Uri.parse("rong://" + ConfigUtils.getInstance().getApplicationContext().getApplicationInfo().processName)
                .buildUpon().appendPath("conversation")
                .appendPath(Conversation.ConversationType.PRIVATE.getName().toLowerCase())
                .appendQueryParameter("targetId", userId)
                .appendQueryParameter("title", name).build();
        RongyunConfig.getInstance().setUserInfoCache(userId, name, avatar);
        startActivity(new Intent("android.intent.action.VIEW", uri));


    }

    /**
     * 打赏
     *
     * @param userId
     * @param typ
     * @param avater
     */
    public static void startRedpacketActivity(String userId, int typ, String avater) {
        if (!UserInfoManager.getInstance().isLogin()) {
            RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
            return;
        }
        Intent intent = new Intent(ConfigUtils.getInstance().getActivityContext(), RedpacketAcitivity.class);
        intent.putExtra("id", userId);
        intent.putExtra("type", typ);
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
    public static void startCertificationActivity() {
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
     *
     * @param address
     * @param height
     * @param sex
     * @param nike
     */
    public static void startRZSecondActivity(String address, String height, String sex, String nike, String path) {
        Intent intent = new Intent(getContext(), RZSecondActivity.class);
        intent.putExtra("address", address);
        intent.putExtra("height", height);
        intent.putExtra("sex", sex);
        intent.putExtra("nike", nike);
        intent.putExtra("path", path);
        startActivity(intent);
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
     * 提现
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

    /**
     * 发红包看照片
     *
     * @param money
     */
    public static void startPayDynamicRedPackketActivity(String money, String id) {
        if (!UserInfoManager.getInstance().isLogin()) {
            RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
            return;
        }
        Intent intent = new Intent(ConfigUtils.getInstance().getActivityContext(), PayDynamicRedPackketActivity.class);
        intent.putExtra("money", money);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    /**
     * 开启图片查看器
     *
     * @param urls2
     * @param position
     */
    public static void startImagePagerActivity(ArrayList<String> urls2, int position) {
        Intent intent = new Intent(ConfigUtils.getInstance().getActivityContext(), ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        startActivity(intent);
    }

    /**
     * 开启相机
     */
    public static void startTakeVideoActivity() {
        startActivity(TakeVideoActivity.class);
    }

    /**
     * 修改信息
     *
     * @param type
     */
    public static void startPersonInfoChangeActivity(PersonInfoChangeActivity.ChangeType type) {
        Intent intent = new Intent(getContext(), PersonInfoChangeActivity.class);
        intent.putExtra("type", type.getValue());
        startActivity(intent);
    }

    /**
     * 认证三步
     */
    public static void startRZThidActivity() {
        startActivity(RZThidActivity.class);
    }

    /**
     * 约会
     */
    public static void startPayAppointmentActivity() {
        startActivity(PayAppointmentActivity.class);
    }

    /**
     * 约会订单页
     */
    public static void startPayAppointmentOrderActivity() {
        startActivity(PayAppointmentOrderActivity.class);
    }

    /**
     * 提现记录
     *
     * @param dataBean
     */
    public static void startTiXianRecordActivity(TiXinrRecordBean.DataBean dataBean) {
        Intent intent = new Intent(getContext(), TiXianRecordActivity.class);
        intent.putExtra("data", dataBean);
        startActivity(intent);
    }

    /**
     * 添加银行账号
     */
    public static void startAddCardActivity(String bankname, String type) {
        Intent intent = new Intent(getContext(), AddCardActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("bankname", bankname);
        startActivity(intent);
    }

    /**
     * 选择支付银行卡
     *
     * @param type
     * @param number
     */
    public static void startSwitchCardActivity(String type, String number) {
        Intent intent = new Intent(getContext(), SwitchCardActivity.class);
        intent.putExtra("number", number);
        intent.putExtra("type", type);
        startActivity(intent);

    }

    /**
     * 开启搜索页面
     */
    public static void startSearchActivity() {
        startActivity(SearchActivity.class);
    }

    /**
     * 搜索结果
     *
     * @param search
     */
    public static void startSearchReasonActivity(String search) {
        Intent intent = new Intent(getContext(), SearchReasonActivity.class);
        intent.putExtra("key", search);
        startActivity(intent);
    }

    /**
     * 修改资料
     */
    public static void startUserInfoChangeActivity() {
        startActivity(UserInfoChangeActivity.class);
    }

    /**
     * 展示用户信息
     *
     * @param uid
     */
    public static void startUserinfoActivity(String uid) {
        Intent intent = new Intent(getContext(), UserinfoActivity.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    /**
     * 找回密码第二步
     *
     * @param authcode
     */
    public static void startFindPasswordNextActivity(String authcode) {
        Intent intent = new Intent(getContext(), FindPasswordNextActivity.class);
        intent.putExtra("code", authcode);
        startActivity(intent);
    }

    /**
     * 找回密码
     */
    public static void startFindPasswordActivity() {
        startActivity(FindPasswordActivity.class);
    }

    /**
     * 图片裁剪
     */
    public static void startCropImageMainActivity() {
        startActivity(CropImageMainActivity.class);
    }

    /**
     * 开启动态删除
     *
     * @param uid
     * @param id
     */
    public static void startDynamicAtionActivity(String uid, String id) {
        Intent intent = new Intent(getContext(), DynamicAtionActivity.class);
        // 1 自己动态  2 他人的动态
        intent.putExtra("uid", uid);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
