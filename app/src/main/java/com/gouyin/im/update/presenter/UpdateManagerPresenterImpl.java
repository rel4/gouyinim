package com.gouyin.im.update.presenter;

import android.content.Context;
import android.content.pm.PackageManager;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.VersionInfo;
import com.gouyin.im.update.UpdateManager;
import com.gouyin.im.update.model.UpdateManagerModel;
import com.gouyin.im.update.model.UpdateManagerModelImpl;
import com.gouyin.im.update.view.UpdateManagerView;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.PrefUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by jb on 2016/7/14.
 */
public class UpdateManagerPresenterImpl implements UpdateManagerPresenter, BaseIModel.onLoadDateSingleListener<VersionInfo>, BaseIModel.onDownFileleListener {

    private UpdateManagerView view;
    private UpdateManagerModel model;
    private String path;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(UpdateManagerView updateManagerView) {
        this.view = updateManagerView;
        model = new UpdateManagerModelImpl();
    }

    @Override
    public void loadVersionInfo(String apkPath) {
        path = apkPath;
        model.loadVersionInfo(this);
    }


    @Override
    public void onSuccess(VersionInfo versionInfo, BaseIModel.DataType dataType) {
        if (versionInfo == null || versionInfo.getData() == null || !StringUtis.equals(versionInfo.getCode(), AppConstant.code_request_success)) {
            return;
        }
        VersionInfo.DataBean data = versionInfo.getData();
        if (isUpdate(StringUtis.string2Int(data.getVersion()))) {
            PrefUtils.setString(UpdateManager.UPLOAD_DESC, data.getDesc());
            PrefUtils.setString(UpdateManager.UPLOAD_VNAME, data.getTitle());
            PrefUtils.setString(UpdateManager.UPLOAD_FSIZE, data.getSize());
            PrefUtils.setString(UpdateManager.UPLOAD_PDATE, data.getTime());
            model.downFile(data.getUrl(), path, this);
            this.versionInfo = versionInfo;

        }

    }

    private VersionInfo versionInfo;

    @Override
    public void onSuccess() {
        view.transfePageMsg(UIUtils.getStringRes(R.string.down_success));
        view.downApkSuccess(versionInfo);

    }

    @Override
    public void onFailure(String msg) {
        LogUtils.e(this, msg);
    }

    @Override
    public void onProgress(long progress, long total, boolean done) {
        view.onProgress(progress, total, done);
    }

    /**
     * 检查软件是否有更新版本
     *
     * @param
     * @return
     */
    private boolean isUpdate(int version) {
        if (getVersionCode(ConfigUtils.getInstance().getApplicationContext()) >= version) {
            return false;
        }
        return true;
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    private String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


}
