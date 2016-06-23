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

        private Addons addons;
        private Object guard;
        private String follow;
        private Baseinfo baseinfo;

        public void setGuard(Object guard) {
            this.guard = guard;
        }

        public Baseinfo getBaseinfo() {
            return baseinfo;
        }

        public void setBaseinfo(Baseinfo baseinfo) {
            this.baseinfo = baseinfo;
        }

        public class Baseinfo {
            private String nickname;
            private int sex;
            private String face;
            private String like_image;

            public String getLike_image() {
                return like_image;
            }

            public void setLike_image(String like_image) {
                this.like_image = like_image;
            }

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

        public class Addons {
            private String ufann;
            private String ufoln;
            private String ulatn;
            private String uflon;

            public String getUfann() {
                return ufann;
            }

            public void setUfann(String ufann) {
                this.ufann = ufann;
            }

            public String getUfoln() {
                return ufoln;
            }

            public void setUfoln(String ufoln) {
                this.ufoln = ufoln;
            }

            public String getUlatn() {
                return ulatn;
            }

            public void setUlatn(String ulatn) {
                this.ulatn = ulatn;
            }

            public String getUflon() {
                return uflon;
            }

            public void setUflon(String uflon) {
                this.uflon = uflon;
            }
        }

        public Object getGuard() {
            return guard;
        }

        public void setGuard(String guard) {
            this.guard = guard;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public Addons getAddons() {
            return addons;
        }

        public void setAddons(Addons addons) {
            this.addons = addons;
        }


    }

}