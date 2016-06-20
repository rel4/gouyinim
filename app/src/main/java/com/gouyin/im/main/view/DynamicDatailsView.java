package com.gouyin.im.main.view;

import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.BaseDataBean;

import java.util.List;

/**
 * Created by jb on 2016/6/8.
 */
public interface DynamicDatailsView {
    void loadData(List<BaseDataBean> datas);

    void hide();

    void show();
}
