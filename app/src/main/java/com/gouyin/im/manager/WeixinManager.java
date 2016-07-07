package com.gouyin.im.manager;

import com.gouyin.im.utils.ConfigUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by jb on 2016/7/6.
 */
public class WeixinManager {
    private volatile static WeixinManager instance;
   private static  IWXAPI api;
    public static WeixinManager getInstance() {
        if (instance == null) {
            synchronized (UserInfoManager.class) {
                if (instance == null) {
                    api = WXAPIFactory.createWXAPI(ConfigUtils.getInstance().getApplicationContext(), "wxd73266bdf4679ebf");
                    instance = new WeixinManager();
                }

            }
        }
        return instance;
    }
    public void pay(){
        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
        PayReq req = new PayReq();
        req.appId			= "wxd73266bdf4679ebf";
        req.partnerId		= "1359350902";
        req.prepayId		="wx20160706210428dd71bb90be0375310500";
        req.nonceStr		= "zUoJwE94gpTUbAUt";
        req.timeStamp		= "wxd73266bdf4679ebf";
        req.packageValue	= "com.gouyin.im";
        req.sign			= "02C4DDC4482F7A8B78A9CA68AFBA4B23";
        req.extData			= "app data"; // optional
        api.sendReq(req);
    }
}
