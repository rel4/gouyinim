package com.gouyin.im.home.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.GoodSelectBaen;

import java.util.List;

/**
 * Created by pc on 2016/6/3.
 */
public interface GoodSelectModel {
    void loadGoodSelectDate(String type,int page,BaseIModel.onLoadDateSingleListener<List<GoodSelectBaen.Data>> listener);
}
