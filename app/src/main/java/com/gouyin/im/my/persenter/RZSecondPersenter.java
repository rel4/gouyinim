package com.gouyin.im.my.persenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.my.view.RZSecondView;

import java.util.ArrayList;

/**
 * Created by jb on 2016/6/30.
 */
public interface RZSecondPersenter extends BaseIPresenter<RZSecondView> {
    void submit(String address1, String address2, String height, String sexid, String nike, String avaterpath, ArrayList<String> pics);

}
