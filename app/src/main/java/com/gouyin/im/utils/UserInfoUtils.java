package com.gouyin.im.utils;

import com.gouyin.im.AppConstant;
import com.gouyin.im.CacheManager;
import com.gouyin.im.bean.LoginBean;
import com.gouyin.im.bean.PresonInfo;

import io.rong.imkit.RongyunConfig;

/**
 * Created by jb on 2016/6/20.
 */
public class UserInfoUtils {
    private static String authcode = "";

    public static String getUserId() {

        return null;
    }

    public static String getAuthcode() {
        if (!isLogin())
            return "";
        if (StringUtis.isEmpty(authcode)) {
            PresonInfo presonInfo = getPresonInfo();
            if (presonInfo != null)
                authcode = presonInfo.getAuthcode();
        }
        return authcode;
    }

    public static void setAuthcode(String code) {
        authcode = code;
        PresonInfo presonInfo = getPresonInfo();
        presonInfo.setAuthcode(authcode);
        saveUserInfo(presonInfo);
    }

    public static boolean isLogin() {
        return PrefUtils.getBoolean(AppConstant.FLAG_LOGIN_STATUS, false);
    }

    /**
     * 获取信息
     *
     * @return
     */
    public static PresonInfo getPresonInfo() {
        Object o = CacheManager.readObject(ConfigUtils.getInstance().getApplicationContext(), CacheManager.CachePath.FLAG_LOGIN_CODE);
        if (o != null && o instanceof PresonInfo) {
            PresonInfo info = (PresonInfo) o;
            return info;

        }
        return null;
    }

    /**
     * 获取融云key
     *
     * @return
     */
    public static String getRongyunKey() {
        return getPresonInfo().getRongyunkey();
    }

    /**
     * 保存用户信息对象
     *
     * @param info
     */
    public static void saveUserInfo(PresonInfo info) {
        authcode = "";
        CacheManager.saveObject(ConfigUtils.getInstance().getApplicationContext(), info, CacheManager.CachePath.FLAG_LOGIN_CODE);
    }

    /**
     * 用户下线
     */
    public static void offline() {
        authcode = "";
        PrefUtils.setBoolean(AppConstant.FLAG_LOGIN_STATUS, false);
        saveUserInfo(null);
        RongyunConfig.getInstance().offline();

    }
}
