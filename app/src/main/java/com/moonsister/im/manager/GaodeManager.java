package com.moonsister.im.manager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.utils.ConfigUtils;
import com.moonsister.im.utils.LogUtils;
import com.moonsister.im.utils.PrefUtils;

/**
 * Created by jb on 2016/7/15.
 */
public class GaodeManager implements AMapLocationListener {
    private volatile static GaodeManager instance;

    private static AMapLocationClient locationClient = null;
    private static AMapLocationClientOption locationOption = null;

    public static GaodeManager getInstance() {
        if (instance == null) {
            synchronized (UserInfoManager.class) {
                if (instance == null) {
                    instance = new GaodeManager();
                    locationClient = new AMapLocationClient(ConfigUtils.getInstance().getApplicationContext());
                    locationOption = new AMapLocationClientOption();
                    // 设置定位模式为高精度模式
                    locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    // 设置定位监听
                }

            }
        }
        return instance;
    }

    public String getLocLocation() {
        initOption();
        locationClient.setLocationListener(this);
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        return null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            PrefUtils.setString(GaodeManager.class.getName(), aMapLocation.toStr());
            RxBus.getInstance().send(Events.EventEnum.GET_LOCLOCATION, null);
        }
        LogUtils.e(this, "--------定位完成---------");
        locationClient.stopLocation();

    }

    // 根据控件的选择，重新设置定位参数
    private void initOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
        locationOption.setInterval(1000);


    }


}
