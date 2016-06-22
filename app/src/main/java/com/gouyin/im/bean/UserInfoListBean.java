package com.gouyin.im.bean;

import java.util.List;

public class UserInfoListBean extends BaseBean {

    private UserInfoListBeanData data;


    public UserInfoListBeanData getData() {
        return this.data;
    }

    public void setData(UserInfoListBeanData data) {
        this.data = data;
    }

    public class UserInfoListBeanData {
        private List<UserInfoListBeanDataList> list;

        public List<UserInfoListBeanDataList> getList() {
            return this.list;
        }

        public void setList(List<UserInfoListBeanDataList> list) {
            this.list = list;
        }

        public class UserInfoListBeanDataList extends BaseDataBean {
            private int lkpicn;
            private List<String> img;
            private long create_time;
            private String latest_id;
            private List<String> simg;
            private int lupn;
            private int lcomn;
            private String litpic;
            private String title;
            private int type;
            private int lredn;
            private String vimg;
            private String face;
            private String nickname;
            private String sex;
            private String uid;

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public String getLatest_id() {
                return latest_id;
            }

            public void setLatest_id(String latest_id) {
                this.latest_id = latest_id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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

            public String getVimg() {
                return vimg;
            }

            public void setVimg(String vimg) {
                this.vimg = vimg;
            }

            public int getLkpicn() {
                return this.lkpicn;
            }

            public void setLkpicn(int lkpicn) {
                this.lkpicn = lkpicn;
            }

            public List<String> getImg() {
                return img;
            }

            public void setImg(List<String> img) {
                this.img = img;
            }

            public List<String> getSimg() {
                return simg;
            }

            public void setSimg(List<String> simg) {
                this.simg = simg;
            }


            public int getLupn() {
                return this.lupn;
            }

            public void setLupn(int lupn) {
                this.lupn = lupn;
            }

            public int getLcomn() {
                return this.lcomn;
            }

            public void setLcomn(int lcomn) {
                this.lcomn = lcomn;
            }

            public String getLitpic() {
                return this.litpic;
            }

            public void setLitpic(String litpic) {
                this.litpic = litpic;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return this.type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getLredn() {
                return this.lredn;
            }

            public void setLredn(int lredn) {
                this.lredn = lredn;
            }
        }
    }
}
