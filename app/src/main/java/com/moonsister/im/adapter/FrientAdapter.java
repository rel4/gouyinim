package com.moonsister.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.moonsister.im.AppConstant;
import com.moonsister.im.R;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.base.BaseRecyclerViewAdapter;
import com.moonsister.im.base.BaseRecyclerViewHolder;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.bean.FrientBaen;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.im.widget.FrientFragment;
import com.moonsister.im.main.model.UserActionModelImpl;
import com.moonsister.im.my.view.FrientFragmentView;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.viewholder.FrientViewHoler;

import java.util.List;

/**
 * Created by jb on 2016/7/8.
 */
public class FrientAdapter extends BaseRecyclerViewAdapter<FrientBaen.DataBean> {

    private FrientViewHoler viewHoler;
    private FrientFragmentView view;
    private int pageType;

    public FrientAdapter(List<FrientBaen.DataBean> list, FrientFragmentView view) {
        super(list);
        this.view = view;
    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        View view = UIUtils.inflateLayout(R.layout.item_frient);
        viewHoler = new FrientViewHoler(view);
        viewHoler.setAdapter(this);
        return viewHoler.getRootView();
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {

        return viewHoler;
    }

    @Override
    public void onItemclick(View view, int position) {
//        if (datas != null && datas.get(position) != null) {
//            FrientBaen.DataBean dataBean = datas.get(position);
//            if (!StringUtis.isEmpty(dataBean.getUid())) {
//                ActivityUtils.startDynamicActivity(dataBean.getUid());
//            }
//
//        }
    }

    public void setClick(Integer position) {
        if (datas.size() - 1 >= position) {
            FrientBaen.DataBean dataBean = datas.get(position);
            if (dataBean == null)
                return;

            view.showLoading();
            UserActionModelImpl userActionModel = new UserActionModelImpl();
            userActionModel.wacthAction(dataBean.getUid(), pageType == FrientFragment.PAGE_FEN ? "1" : "2", new BaseIModel.onLoadDateSingleListener<DefaultDataBean>() {
                @Override
                public void onSuccess(DefaultDataBean bean, BaseIModel.DataType dataType) {
                    if (bean != null) {
                        if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
                            if (StringUtis.equals(dataBean.getIsfollow(), "2")) {
                                dataBean.setIsfollow("1");
                            } else
                                datas.remove(dataBean);
                            onRefresh();
//                            Events<FrientBaen.DataBean> events = new Events<>();
//                            events.what = Events.EventEnum.FRIEND_CHANGE;
//                            events.message = dataBean;
                            RxBus.getInstance().send(Events.EventEnum.FRIEND_CHANGE,null);
                        }
                        view.transfePageMsg(bean.getMsg());
                    } else {
                        view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
                    }
                    view.hideLoading();
                }

                @Override
                public void onFailure(String msg) {
                    view.hideLoading();
                    view.transfePageMsg(msg);
                }
            });

        }
    }

    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

}
