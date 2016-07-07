package com.gouyin.im.home.view;

import com.gouyin.im.base.BaseIView;
import com.gouyin.im.bean.GoodSelectBaen;

import java.util.List;

/**
 * Created by pc on 2016/6/3.
 */
public interface GoodSelectView extends BaseIView {
    void addGoodSelectDate(List<GoodSelectBaen.Data> list);

}
