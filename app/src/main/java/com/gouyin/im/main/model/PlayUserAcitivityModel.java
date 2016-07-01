package com.gouyin.im.main.model;

import com.gouyin.im.base.BaseIModel;

/**
 * Created by jb on 2016/6/24.
 */
public interface PlayUserAcitivityModel extends BaseIModel {
    void aliPay(int type, PayType playType, String uid, String money, onLoadDateSingleListener listener);

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

