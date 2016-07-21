package com.moonsister.tcjy;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.moonsister.tcjy.base.BaseActivity;
import com.moonsister.tcjy.utils.ConfigUtils;

import java.util.ArrayList;

import io.rong.imkit.RongyunConfig;

/**
 * Created by pc on 2016/6/3.
 */
public class ApplicationConfig extends Application {
    AppComponent appComponent;
    private ArrayList<BaseActivity> activities = new ArrayList<BaseActivity>();

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigUtils.getInstance().setApplicationContext(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .serverApiModule(new ServerApiModule(new ServerApi()))
                .build();
//
        initRongYun();
    }

    private void initRongYun() {

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongyunConfig.getInstance().init(this);
        }

    }

    public AppComponent getAppComponent() {

        return appComponent;
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        if (!activities.contains(activity))
            activities.add(activity);
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity) {
        if (activities.contains(activity))
            activities.remove(activity);
    }

    public void logout() {
        for (int i = 0; i < activities.size(); i++) {
            activities.get(i).finish();
        }
        activities.clear();
    }


}
