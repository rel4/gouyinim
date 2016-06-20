package com.gouyin.im.bean;

/**
 * Created by jb on 2016/6/20.
 */
public class UserInfoDetailBean extends BaseBean {
    private UserInfoDetailDataBean data;

    public UserInfoDetailDataBean getData() {
        return data;
    }

    public void setData(UserInfoDetailDataBean data) {
        this.data = data;
    }

    public class UserInfoDetailDataBean extends BaseDataBean {
        private String nickname;
        private int sex;
        private String face;
        private String like_image;

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getSex() {
            return sex;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getFace() {
            return face;
        }

        public void setLikeImage(String likeImage) {
            this.like_image = likeImage;
        }

        public String getLikeImage() {
            return like_image;
        }
    }
}
