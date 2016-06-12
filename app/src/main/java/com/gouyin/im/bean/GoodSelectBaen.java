package com.gouyin.im.bean;

/**
 * Created by pc on 2016/6/3.
 */
public class GoodSelectBaen extends BaseBean {

    private String data;

    private String code;

    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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

    @Override
    public String toString() {
        return "GoodSelectBaen{" +
                "data='" + data + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
