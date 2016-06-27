package com.gouyin.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gouyin.im.R;


/**
 * Created by Administrator on 2016/6/26.
 */
public class RZGridViewAdapter extends BaseAdapter {

    private Context mContext;

    public RZGridViewAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 6;
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_renzheng_grid_item, null);
        return convertView;
    }
}