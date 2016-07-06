package com.gouyin.im.bean;

/**
 * Created by jb on 2016/6/26.
 */
public class PersonInfoDetail extends BaseDataBean {


    private String authcode;
    private String face;
    private String rongyunkey;
    private String nickname;
    private boolean isLogin;
    private String sex;
    /**
     * 认证状态 1 已认证  2 认证中  3 未认证
     */
    private int attestation;

    /**
     * 1男 2 女
     *
     * @return
     */
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

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

    public void setAttestation(int attestation) {
        this.attestation = attestation;
    }

    public int getAttestation() {
        return attestation;
    }
}
