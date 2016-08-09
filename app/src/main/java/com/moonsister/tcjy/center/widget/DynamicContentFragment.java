package com.moonsister.tcjy.center.widget;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.moonsister.tcjy.ImageServerApi;
import com.moonsister.tcjy.R;
import com.moonsister.tcjy.base.BaseFragment;
import com.moonsister.tcjy.event.Events;
import com.moonsister.tcjy.event.RxBus;
import com.moonsister.tcjy.main.widget.PictureSelectorActivity;
import com.moonsister.tcjy.main.widget.VideoSelectorActivity;
import com.moonsister.tcjy.utils.ActivityUtils;
import com.moonsister.tcjy.utils.StringUtis;
import com.moonsister.tcjy.utils.UIUtils;
import com.moonsister.tcjy.widget.NoScrollGridView;
import com.moonsister.tcjy.widget.RoundedImageView;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/8/9.
 */
public class DynamicContentFragment extends BaseFragment {
    @Bind(R.id.iv_add_content)
    ImageView ivAddContent;
    @Bind(R.id.rl_root_content)
    RelativeLayout root;
    private NoScrollGridView noScrollGridView;
    private ArrayList pics;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UIUtils.inflateLayout(R.layout.fragment_dynamic_content);
    }

    @Override
    protected void initData() {
        RxBus.with(this)
                .setEndEvent(FragmentEvent.DESTROY)
                .setEvent(Events.EventEnum.SWITCH_ITEM)
                .onNext(events -> {
                    if (events != null) {
                        Object message = events.message;
                        if (message != null && message instanceof Bundle) {
                            Bundle bundle = (Bundle) message;
                            String tag = bundle.getString("tag");
                            if (StringUtis.equals(tag, this.getClass().getName())) {
                                String id = bundle.getString("id");
                                doClick(id);
                            }
                        }
                    }
                })
                .create();
    }

    private void doClick(String id) {
        switch (id) {
            case "1":
                startPicActivityForResult();

                noScrollGridView = new NoScrollGridView(getActivity());
                noScrollGridView.setNumColumns(3);
                noScrollGridView.setHorizontalSpacing(20);
                noScrollGridView.setVerticalSpacing(20);
                root.addView(noScrollGridView);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) noScrollGridView.getLayoutParams();
                int dimension = (int) getResources().getDimension(R.dimen.x30);
                params.topMargin = dimension;
                params.bottomMargin = dimension;
                params.leftMargin = dimension;
                params.rightMargin = dimension;
                noScrollGridView.setLayoutParams(params);
                break;
            case "2":
                ActivityUtils.startActivity(VideoSelectorActivity.class);
                break;
            case "3":
                break;
        }

    }

    public static Fragment newInsatance() {
        return new DynamicContentFragment();
    }


    @OnClick(R.id.iv_add_content)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_content:
                HashMap<String, String> map = new HashMap<>();
                map.put("1", UIUtils.getStringRes(R.string.pic));
                map.put("2", UIUtils.getStringRes(R.string.video));
                map.put("3", UIUtils.getStringRes(R.string.voice));
                ActivityUtils.startSwitchItemActivity(map, this.getClass().getName());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (requestCode == 1) {
            ArrayList listExtra = data.getStringArrayListExtra("android.intent.extra.RETURN_RESULT");
            if (listExtra == null)
                return;
            if (pics == null)
                pics = new ArrayList();
            if (pics.size() > 0) {
                Object o = pics.get(pics.size() - 1);
                if (o instanceof Integer)
                    pics.remove(pics.size() - 1);
            }
            if (pics.size() + listExtra.size() > 9) {
                pics.add(R.mipmap.add_images);
                showToast(UIUtils.getStringRes(R.string.pic_more_nine));
                return;
            }
            pics.addAll(listExtra);
            if (pics.size() < 9 && pics.size() > 0) {
                if (pics.get(pics.size() - 1) instanceof Integer) {
                } else {
                    pics.add(R.mipmap.add_images);
                }

            }
            ivAddContent.setVisibility(View.GONE);
            ShowPicAdapter showPicAdapter = new ShowPicAdapter(pics);
            noScrollGridView.setAdapter(showPicAdapter);
        }
    }

    private void startPicActivityForResult() {
        Intent intent = new Intent(getActivity(), PictureSelectorActivity.class);
        int munber = 0;
        if (pics != null) {
            munber = pics.size();
            if (pics.size() > 0) {
                Object o = pics.get(pics.size() - 1);
                if (o instanceof Integer)
                    munber = munber - 1;
            }
        }
        intent.putExtra("max", munber);
        startActivityForResult(intent, 1);
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


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = UIUtils.inflateLayout(R.layout.item_dynamic_publish);
            ImageView pic = (ImageView) view.findViewById(R.id.iv_pic);
            View delete = view.findViewById(R.id.iv_delete_pic);
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pics.remove(position);
//                    datas.remove(position);
                    if (pics.size() == 0)
                        ivAddContent.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
            });
            Object o = datas.get(position);
            if (o != null) {
                if (o instanceof String) {
                    ImageServerApi.showURLSamllImage(pic, (String) o);

                } else if (o instanceof Integer) {
                    delete.setVisibility(View.GONE);
                    ImageServerApi.showResourcesImage(pic, (Integer) o);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPicActivityForResult();

                        }
                    });
                } else if (o instanceof Uri) {
                    ImageServerApi.showURLBigImage(pic, ((Uri) o).getPath());
                }
            }
            return view;
        }
    }
}
