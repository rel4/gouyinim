package com.gouyin.im.my.widget;

import android.view.View;

import com.gouyin.im.R;
import com.gouyin.im.adapter.SwitchCardAdatper;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.bean.CardInfoBean;
import com.gouyin.im.bean.GetMoneyBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.my.persenter.SwitchCardActivityPresenter;
import com.gouyin.im.my.persenter.SwitchCardActivityPresenterImpl;
import com.gouyin.im.my.view.SwitchCardActivityView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.XListView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by jb on 2016/7/4.
 */
public class SwitchCardActivity extends BaseActivity implements SwitchCardActivityView {
    @Bind(R.id.xlv)
    XListView xlv;
    private SwitchCardActivityPresenter presenter;

    @Override
    protected View setRootContentView() {
        presenter = new SwitchCardActivityPresenterImpl();
        presenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.activity_switch_card);
    }

    @Override
    protected void initView() {
        xlv.setLoadingMoreEnabled(false);
        xlv.setPullRefreshEnabled(false);
        xlv.setVerticalLinearLayoutManager();
        presenter.loadCardInfo();
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void transfePageMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void setCardInfos(List<CardInfoBean.DataBean> datas) {
        String number = getIntent().getStringExtra("number");
        for (CardInfoBean.DataBean dataBean : datas) {
            if (StringUtis.equals(dataBean.getBank_no(), number))
                dataBean.setIs_default("1");
        }
        SwitchCardAdatper adatper = new SwitchCardAdatper(datas);
        adatper.setOnItemClickListener(new BaseRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemclick(View view, int position) {
                CardInfoBean.DataBean dataBean = datas.get(position);
                GetMoneyBean bean = new GetMoneyBean();

                GetMoneyBean.DataBean data = new GetMoneyBean.DataBean();
                data.setBank_name(dataBean.getBank_name());
                data.setLogo(dataBean.getLogo());
                data.setBank_no(dataBean.getBank_no());
                bean.setData(data);

                Events<GetMoneyBean> events = new Events<>();
                events.what = Events.EventEnum.CLICK_SWITCH_CARD_POSITION;
                events.message = bean;
                RxBus.getInstance().send(events);
                SwitchCardActivity.this.finish();

            }
        });
        xlv.setAdapter(adatper);


    }
}
