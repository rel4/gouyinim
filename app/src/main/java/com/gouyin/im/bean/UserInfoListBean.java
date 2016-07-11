package com.gouyin.im.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserInfoListBean extends BaseBean {

    private UserInfoListBeanData data;


    public UserInfoListBeanData getData() {
        return this.data;
    }

    public void setData(UserInfoListBeanData data) {
        this.data = data;
    }

    public static  class UserInfoListBeanData {
        private List<UserInfoListBeanDataList> list;

        public List<UserInfoListBeanDataList> getList() {
            return this.list;
        }

        public void setList(List<UserInfoListBeanDataList> list) {
            this.list = list;
        }

        public static class UserInfoListBeanDataList extends BaseDataBean {
            private String lkpicn;
            private ArrayList<String> img;
            private long create_time;
            private String latest_id;
            private List<String> simg;
            private String lupn;
            private String lcomn;
            private String litpic;
            private String title;
            private int type;
            private String lredn;
            private String vimg;
            private String face;
            private String nickname;
            private String sex;
            private String uid;
            private String money;
            private String video;
            private String tmoney;

            public String getTmoney() {
                return tmoney;
            }

            public void setTmoney(String tmoney) {
                this.tmoney = tmoney;
            }

            public String getIspay() {
                return ispay;
            }

            public void setIspay(String ispay) {
                this.ispay = ispay;
            }

            /**
             * 1 已支付  2 未支付
             */

            private String ispay;

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getLkpicn() {
                return lkpicn;
            }

            public void setLkpicn(String lkpicn) {
                this.lkpicn = lkpicn;
            }

            public ArrayList<String> getImg() {
                return img;
            }

            public void setImg(ArrayList<String> img) {
                this.img = img;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public List<String> getSimg() {
                return simg;
            }

            public void setSimg(List<String> simg) {
                this.simg = simg;
            }

            public String getLatest_id() {
                return latest_id;
            }

            public void setLatest_id(String latest_id) {
                this.latest_id = latest_id;
            }

            public String getLupn() {
                return lupn;
            }

            public void setLupn(String lupn) {
                this.lupn = lupn;
            }

            public String getLcomn() {
                return lcomn;
            }

            public void setLcomn(String lcomn) {
                this.lcomn = lcomn;
            }

            public String getLitpic() {
                return litpic;
            }

            public void setLitpic(String litpic) {
                this.litpic = litpic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getVimg() {
                return vimg;
            }

            public void setVimg(String vimg) {
                this.vimg = vimg;
            }

            public String getLredn() {
                return lredn;
            }

            public void setLredn(String lredn) {
                this.lredn = lredn;
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

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public static class Simg implements Serializable {
                private String url;

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getUrl() {
                    return this.url;
                }

            }

            public static  class Img implements Serializable {
                private String url;

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getUrl() {
                    return this.url;
                }

            }


        }
    }
}
