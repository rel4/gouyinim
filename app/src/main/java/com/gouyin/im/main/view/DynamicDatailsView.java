package com.gouyin.im.main.view;

import com.gouyin.im.bean.BaseBean;

import java.util.List;

/**
 * Created by jb on 2016/6/8.
 */
public interface DynamicDatailsView {
    void loadData(List<BaseBean> datas);

    void hide();

    void show();
}
