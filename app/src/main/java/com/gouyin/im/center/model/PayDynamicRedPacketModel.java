package com.gouyin.im.center.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.PayRedPacketPicsBean;

/**
 * Created by jb on 2016/6/29.
 */
public interface PayDynamicRedPacketModel extends BaseIModel {
    void pay(String id, PayType alipay, onLoadDateSingleListener<PayRedPacketPicsBean> listener);

    void getPics(String id,onLoadDateSingleListener<PayRedPacketPicsBean> listener);

    public enum PayType {
        ALIPAY("alipay"), WXPAY("wxpay");
        private final String type;

        private PayType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
