package com.gouyin.im.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jb on 2016/6/29.
 */
public class PayRedPacketPicsBean extends BaseDataBean {


    /**
     * code : 1
     * msg : 成功
     * data : {"simg":["http://mimei.img-cn-beijing.aliyuncs.com/image/20160628/20/14671166575394752.jpg@!200_200","http://mimei.img-cn-beijing.aliyuncs.com/image/20160628/20/14671166583119343.jpg@!200_200","http://mimei.img-cn-beijing.aliyuncs.com/image/20160628/20/14671166589453508.jpg@!200_200","http://mimei.img-cn-beijing.aliyuncs.com/image/20160628/20/14671166599613940.jpg@!200_200"],"img":["http://mimei.oss-cn-beijing.aliyuncs.com/image/20160628/20/14671166575394752.jpg","http://mimei.oss-cn-beijing.aliyuncs.com/image/20160628/20/14671166583119343.jpg","http://mimei.oss-cn-beijing.aliyuncs.com/image/20160628/20/14671166589453508.jpg","http://mimei.oss-cn-beijing.aliyuncs.com/image/20160628/20/14671166599613940.jpg"]}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> simg;
        private ArrayList<String> img;
        private String latest_id;

        public String getLatest_id() {
            return latest_id;
        }

        public void setLatest_id(String latest_id) {
            this.latest_id = latest_id;
        }

        public List<String> getSimg() {
            return simg;
        }

        public void setSimg(List<String> simg) {
            this.simg = simg;
        }

        public ArrayList<String> getImg() {
            return img;
        }

        public void setImg(ArrayList<String> img) {
            this.img = img;
        }
    }
}
