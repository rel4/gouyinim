package com.gouyin.im;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.gouyin.im.utils.ConfigUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by jb on 2016/6/15.
 */
public class ImageServerApi {
    private static volatile Picasso mPicasso;
    private static int errorImage = R.mipmap.ic_launcher;
    private static int loadingImage = R.mipmap.affirm_icon;

    private static Picasso getPicasso() {
        if (mPicasso == null) {
            synchronized (ImageServerApi.class) {
                if (mPicasso == null) {
                    return Picasso.with(ConfigUtils.getInstance().getApplicationContext());
                }
                return mPicasso;

            }
        }
        return mPicasso;
    }

    /**
     * 显示url
     *
     * @param imageView
     * @param url
     */
    public static void showURLImage(@NonNull ImageView imageView, @NonNull String url) {


        if (url.startsWith("http"))

            getPicasso().load(url).placeholder(loadingImage).error(errorImage).into(imageView);
        else {
            getPicasso().load(new File(url)).placeholder(loadingImage).error(errorImage).into(imageView);
        }
    }

    public static void showResourcesImage(@NonNull ImageView imageView, int resourceId) {

        getPicasso().load(resourceId).placeholder(loadingImage).error(errorImage).into(imageView);
    }


}
