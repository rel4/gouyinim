package com.gouyin.im.utils;

import com.gouyin.im.AppConstant;
import com.gouyin.im.CacheManager;
import com.gouyin.im.bean.LoginBean;

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
            LoginBean.PresonInfo presonInfo = getPresonInfo();
            if (presonInfo != null)
                authcode = presonInfo.getAuthcode();
        }
        return authcode;
    }

    public static void setAuthcode(String code) {
        authcode = code;
    }

    public static boolean isLogin() {
        return PrefUtils.getBoolean(AppConstant.FLAG_LOGIN_STATUS, false);
    }

    /**
     * 用户信息
     *
     * @return
     */
    private static LoginBean.PresonInfo getPresonInfo() {
        Object o = CacheManager.readObject(ConfigUtils.getInstance().getApplicationContext(), CacheManager.CachePath.FLAG_LOGIN_CODE);
        if (o != null && o instanceof LoginBean.PresonInfo) {
            LoginBean.PresonInfo info = (LoginBean.PresonInfo) o;
            return info;

        }
        return null;
    }
}
