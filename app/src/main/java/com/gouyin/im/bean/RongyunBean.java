package com.gouyin.im.bean;

/**
 * Created by jb on 2016/6/26.
 */
public class RongyunBean extends BaseBean {
    private RongyunData data;

    public RongyunData getData() {
        return data;
    }

    public void setData(RongyunData data) {
        this.data = data;
    }

    public class RongyunData {
        private String token;
        private String face;
        private String nickname;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
