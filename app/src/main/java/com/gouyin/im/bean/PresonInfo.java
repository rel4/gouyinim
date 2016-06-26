package com.gouyin.im.bean;

/**
 * Created by jb on 2016/6/26.
 */
public class PresonInfo  extends BaseDataBean{


    private String authcode;
    private String face;
    private String rongyunkey;
    private String nickname;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getRongyunkey() {
        return rongyunkey;
    }

    public void setRongyunkey(String rongyunkey) {
        this.rongyunkey = rongyunkey;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

}
