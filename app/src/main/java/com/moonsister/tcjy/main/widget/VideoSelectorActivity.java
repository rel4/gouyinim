package com.moonsister.tcjy.main.widget;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moonsister.tcjy.R;
import com.moonsister.tcjy.utils.DateUtils;
import com.moonsister.tcjy.utils.TextFormater;
import com.moonsister.tcjy.widget.RecyclingImageView;
import com.moonsister.tcjy.widget.takevideo.TakeVideoActivity;
import com.moonsister.tcjy.widget.takevideo.VideoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jb on 2016/8/9.
 */
public class VideoSelectorActivity extends Activity implements AdapterView.OnItemClickListener {
    List<VideoEntity> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_selector);
        mList = new ArrayList<VideoEntity>();
        getVideoFile();
        final GridView mGridView = (GridView) findViewById(R.id.gridView);
        ImageAdapter mAdapter = new ImageAdapter(getApplicationContext());
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

    }

    private void getVideoFile() {
        ContentResolver mContentResolver = getContentResolver();
        Cursor cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.DEFAULT_SORT_ORDER);

        if (cursor != null && cursor.moveToFirst()) {
            do {

                // ID:MediaStore.Audio.Media._ID
                int id = cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media._ID));

                // 名称：MediaStore.Audio.Media.TITLE
                String title = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                // 路径：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                // 总播放时长：MediaStore.Audio.Media.DURATION
                int duration = cursor
                        .getInt(cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));

                // 大小：MediaStore.Audio.Media.SIZE
                int size = (int) cursor.getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                VideoEntity entty = new VideoEntity();
                entty.ID = id;
                entty.title = title;
                entty.filePath = url;
                entty.duration = duration;
                entty.size = size;
                mList.add(entty);
            } while (cursor.moveToNext());

        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {

            Intent intent = new Intent();
            intent.setClass(this, TakeVideoActivity.class);
            startActivityForResult(intent, 100);
        } else {
            VideoEntity vEntty = mList.get(position - 1);
            // 限制大小不能超过10M
            if (vEntty.size > 1024 * 1024 * 10) {
                String st = getResources().getString(R.string.temporary_does_not);
                Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = getIntent().putExtra("path", vEntty.filePath).putExtra("dur", vEntty.duration);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    private class ImageAdapter extends BaseAdapter {

        private final Context mContext;
        private int mItemHeight = 0;
        private RelativeLayout.LayoutParams mImageViewLayoutParams;

        public ImageAdapter(Context context) {
            super();
            mContext = context;
            mImageViewLayoutParams = new RelativeLayout.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        }

        @Override
        public int getCount() {
            return mList.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return (position == 0) ? null : mList.get(position - 1);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_choose_gridi, container, false);
                holder.imageView = (RecyclingImageView) convertView.findViewById(R.id.imageView);
                holder.icon = (ImageView) convertView.findViewById(R.id.video_icon);
                holder.tvDur = (TextView) convertView.findViewById(R.id.chatting_length_iv);
                holder.tvSize = (TextView) convertView.findViewById(R.id.chatting_size_iv);
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.imageView.setLayoutParams(mImageViewLayoutParams);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // Check the height matches our calculated column width
            if (holder.imageView.getLayoutParams().height != mItemHeight) {
                holder.imageView.setLayoutParams(mImageViewLayoutParams);
            }

            // Finally load the image asynchronously into the ImageView, this
            // also takes care of
            // setting a placeholder image while the background thread runs
            String st1 = getResources().getString(R.string.Video_footage);
            if (position == 0) {
                holder.icon.setVisibility(View.GONE);
                holder.tvDur.setVisibility(View.GONE);
                holder.tvSize.setText(st1);
                holder.imageView.setImageResource(R.mipmap.actionbar_camera_icon);
            } else {
                holder.icon.setVisibility(View.VISIBLE);
                VideoEntity entty = mList.get(position - 1);
                holder.tvDur.setVisibility(View.VISIBLE);

                holder.tvDur.setText(DateUtils.toTime(entty.duration));
                holder.tvSize.setText(TextFormater.getDataSize(entty.size));
                holder.imageView.setImageResource(R.mipmap.empty_photo);
//                mImageResizer.loadImage(entty.filePath, holder.imageView);
            }
            return convertView;
            // END_INCLUDE(load_gridview_item)
        }

        /**
         * Sets the item height. Useful for when we know the column width so the
         * height can be set to match.
         *
         * @param height
         */
        public void setItemHeight(int height) {
            if (height == mItemHeight) {
                return;
            }
            mItemHeight = height;
            mImageViewLayoutParams = new RelativeLayout.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, mItemHeight);
            notifyDataSetChanged();
        }


        class ViewHolder {

            RecyclingImageView imageView;
            ImageView icon;
            TextView tvDur;
            TextView tvSize;


        }


    }

}
