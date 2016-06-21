package com.gouyin.im.bean;

public class UserInfoListBeanDataList extends BaseDataBean {
    private int lkpicn;
    private String img;
    private int create_time;
    private int lupn;
    private int lcomn;
    private String litpic;
    private String title;
    private int lredn;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLkpicn() {
        return this.lkpicn;
    }

    public void setLkpicn(int lkpicn) {
        this.lkpicn = lkpicn;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
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

    public int getLredn() {
        return this.lredn;
    }

    public void setLredn(int lredn) {
        this.lredn = lredn;
    }
}
