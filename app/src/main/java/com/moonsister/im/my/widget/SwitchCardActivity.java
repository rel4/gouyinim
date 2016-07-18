package com.moonsister.im.my.widget;

import android.view.View;

import com.moonsister.im.R;
import com.moonsister.im.adapter.SwitchCardAdatper;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.base.BaseRecyclerViewAdapter;
import com.moonsister.im.bean.CardInfoBean;
import com.moonsister.im.bean.GetMoneyBean;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.my.persenter.SwitchCardActivityPresenter;
import com.moonsister.im.my.persenter.SwitchCardActivityPresenterImpl;
import com.moonsister.im.my.view.SwitchCardActivityView;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.widget.XListView;

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
    protected String initTitleName() {
        return UIUtils.getStringRes(R.string.withdraw_deposit);
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
        String type =getIntent().getStringExtra("type");
        String number = getIntent().getStringExtra("number");
        for (CardInfoBean.DataBean dataBean : datas) {
            if (StringUtis.equals(dataBean.getBank_no(), number)&&StringUtis.equals(type,dataBean.getType()))
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
