package com.gouyin.im.bean;

/**
 * Created by jb on 2016/6/17.
 */
public class LoginBean extends BaseBean {
    private PresonInfo data;

    public PresonInfo getData() {
        return data;
    }

    public void setData(PresonInfo data) {
        this.data = data;
    }

    public class PresonInfo extends BaseDataBean {


        private String authcode;

        public String getAuthcode() {
            return authcode;
        }

        public void setAuthcode(String authcode) {
            this.authcode = authcode;
        }

    }
}
