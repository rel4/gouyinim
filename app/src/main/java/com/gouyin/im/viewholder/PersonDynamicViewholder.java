package com.gouyin.im.viewholder;


import android.view.View;
import android.widget.ImageView;

import com.gouyin.im.R;
import com.gouyin.im.base.PersonDynamicHeadBean;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;

/**
 * Created by jb on 2016/6/25.
 */
public class PersonDynamicViewholder extends BaseHolder<PersonDynamicHeadBean> {
    @Bind(R.id.user_background)
    ImageView userBackground;


    @Override
    protected View initView() {
        View headView = UIUtils.inflateLayout(R.layout.head_person_info);
        return headView;
    }

    @Override
    public void refreshView(PersonDynamicHeadBean data) {
        userBackground.setBackgroundResource(R.mipmap.ic_launcher);
    }
}
