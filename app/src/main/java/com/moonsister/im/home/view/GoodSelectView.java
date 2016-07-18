package com.moonsister.im.home.view;

import com.moonsister.im.base.BaseIView;
import com.moonsister.im.bean.GoodSelectBaen;

import java.util.List;

/**
 * Created by pc on 2016/6/3.
 */
public interface GoodSelectView extends BaseIView {
    void addGoodSelectDate(List<GoodSelectBaen.Data> list);

}
