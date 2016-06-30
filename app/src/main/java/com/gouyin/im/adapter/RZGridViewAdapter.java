package com.gouyin.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.utils.UIUtils;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/6/26.
 */
public class RZGridViewAdapter extends BaseAdapter {

    private ArrayList<String> pics;

    public RZGridViewAdapter(ArrayList<String> pics) {
        this.pics = pics;
    }

    @Override
    public int getCount() {
        return pics == null ? 0 : pics.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = UIUtils.inflateLayout(R.layout.item_pics);
        ImageView pic = (ImageView) view.findViewById(R.id.iv_pic);
        ImageServerApi.showURLSamllImage(pic, pics.get(position));
        return view;
    }
}