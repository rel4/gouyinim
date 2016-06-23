package com.gouyin.im.center.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.center.presenter.DefaultDynamicPresenter;
import com.gouyin.im.center.presenter.DefaultDynamicPresenterImpl;
import com.gouyin.im.center.view.DefaultDynamicView;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.NoScrollGridView;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/23.
 */
public class DefaultDynamicSendActivity extends BaseActivity implements DefaultDynamicView {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.gv_pic_list)
    NoScrollGridView gvPicList;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.iv_switch)
    ImageView ivSwitch;
    private DefaultDynamicPresenter presenter;
    private ShowPicAdapter showPicAdapter;
    private List<String> datas;

    @Override
    protected View setRootContentView() {
        presenter = new DefaultDynamicPresenterImpl();
        presenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.activity_default_dynamic_send);
    }


    @Override
    protected void initView() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(R.mipmap.ic_launcher);
        showPicAdapter = new ShowPicAdapter(objects);
        gvPicList.setAdapter(showPicAdapter);
    }


    @OnClick({R.id.tv_send_submit, R.id.tv_address, R.id.iv_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_submit:
                if (datas == null || datas.size() == 0) {
                    showToast(getResources().getString(R.string.photo) + getResources().getString(R.string.not_empty));
                    break;
                }
                String content = etContent.getText().toString().trim();
                if (StringUtis.isEmpty(content)) {
                    showToast(getResources().getString(R.string.not_empty));
                    break;
                }
                String address = tvAddress.getText().toString().trim();
                String s = datas.get(datas.size() - 1);

                presenter.sendDynamic(content, datas, address);
                break;
            case R.id.tv_address:
                break;
            case R.id.iv_switch:
                break;
        }
    }

    private void setRxBus() {
        RxBus.with(this).setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.GET_PHOTO_LIST)
                .onNext(events ->
                        {

                            if (events != null) {
                                Object message = events.getMessage();
                                if (message != null && message instanceof List) {
                                    List pics = (List) message;
                                    LogUtils.e(DefaultDynamicSendActivity.class, "pics : " + pics.toString());
                                    if (datas == null) {
                                        datas = new ArrayList<String>();

                                    }
                                    datas.clear();
                                    datas.addAll(pics);
                                    pics.add(R.mipmap.password_icon);
                                    showPicAdapter = new ShowPicAdapter(pics);
                                    gvPicList.setAdapter(showPicAdapter);
                                }
                            }
                        }
                ).create();
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

    private class ShowPicAdapter extends BaseAdapter {
        private List datas;

        public ShowPicAdapter(List datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void addDatas(List data) {
            for (int i = 0; i < data.size(); i++)
                datas.add(0, data.get(i));
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = UIUtils.inflateLayout(R.layout.item_pics);
            ImageView pic = (ImageView) view.findViewById(R.id.iv_pic);
            Object o = datas.get(position);
            if (o != null) {
                if (o instanceof String) {
                    ImageServerApi.showURLImage(pic, (String) o);

                } else if (o instanceof Integer) {
                    ImageServerApi.showResourcesImage(pic, (Integer) o);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityUtils.startPhonePicActivity();
                            setRxBus();
                        }
                    });
                }
            }
            return view;
        }
    }
}
