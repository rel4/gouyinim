package com.gouyin.im.bean;

import java.util.List;

/**
 * Created by jb on 2016/6/22.
 */
public class CommentDataListBean extends BaseBean {
    private List<CommentListBean> data;

    public List<CommentListBean> getData() {
        return data;
    }

    public void setData(List<CommentListBean> data) {
        this.data = data;
    }

    public class CommentListBean extends BaseDataBean {
        private int uid;
        private String face;
        private int create_time;
        private int lid;
        private Object nickname;
        private int pid;

        private String title;
        private int status;

        public int getUid() {
            return this.uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getFace() {
            return this.face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getCreate_time() {
            return this.create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getLid() {
            return this.lid;
        }

        public void setLid(int lid) {
            this.lid = lid;
        }

        public Object getNickname() {
            return this.nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public int getPid() {
            return this.pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
