package com.gouyin.im.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pc on 2016/6/3.
 */
public class UIUtils {
    /**
     * 填充布局
     *
     * @param resID
     * @return
     */
    public static View inflateLayout(int resID) {
        return LayoutInflater.from(ConfigUtils.getInstance().getApplicationContext()).inflate(resID, null);
    }

    /**
     * 填充布局
     *
     * @param resID
     * @return
     */
    public static View inflateLayout(int resID, ViewGroup root) {
        return LayoutInflater.from(ConfigUtils.getInstance().getApplicationContext()).inflate(resID, root, false);
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

    public static void onRunMainThred(Runnable r) {
       ConfigUtils.getInstance().getMainHandler().post(r);
    }
}
