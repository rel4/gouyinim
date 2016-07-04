package com.gouyin.im.my.persenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.my.view.GetMoneyView;

/**
 * Created by jb on 2016/7/3.
 */
public interface GetMoneyPersenter  extends BaseIPresenter<GetMoneyView>{
    void loadbasicInfo();

    void PaySubmit(String number, int money);
}
