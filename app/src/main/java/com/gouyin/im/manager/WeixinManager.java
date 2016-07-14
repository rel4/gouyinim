package com.gouyin.im.manager;

import com.gouyin.im.bean.PayBean;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by jb on 2016/7/6.
 */
public class WeixinManager {
    private volatile static WeixinManager instance;
    private static IWXAPI api;

    public static WeixinManager getInstance() {
        if (instance == null) {
            synchronized (UserInfoManager.class) {
                if (instance == null) {
                    api = WXAPIFactory.createWXAPI(ConfigUtils.getInstance().getApplicationContext(), WXPayEntryActivity.APP_ID);
                    instance = new WeixinManager();
                }

            }
        }
        return instance;
    }

    public void pay(PayBean.DataBean data) {
        PayReq req = new PayReq();
        req.appId = data.getAppid();
        req.partnerId = data.getPartnerid();
        req.prepayId = data.getPrepayid();
        req.nonceStr = data.getNoncestr();
        req.timeStamp = data.getTimestamp();
        req.packageValue = data.getPackageX();
        req.sign = data.getSign();
        req.extData = "app data"; // optional
        api.sendReq(req);
    }
}
