package com.moonsister.im.home.model;

import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.GoodSelectBaen;

import java.util.List;

/**
 * Created by pc on 2016/6/3.
 */
public interface GoodSelectModel {
    void loadGoodSelectDate(int pageType, String type, int page, BaseIModel.onLoadDateSingleListener<List<GoodSelectBaen.Data>> listener);
}
