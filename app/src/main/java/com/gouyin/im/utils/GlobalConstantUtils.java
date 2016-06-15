package com.gouyin.im.utils;

import java.io.File;

/**
 * Created by pc on 2016/6/14.
 */
public interface GlobalConstantUtils {
    String PACKAGE_NAME = ConfigUtils.getInstance().getApplicationContext().getPackageName().substring(ConfigUtils.getInstance().getApplicationContext().getPackageName().lastIndexOf(".") + 1);
    String HEAD_ICON_SAVEPATH =SDUtils.getSDPath() + File.separator+ PACKAGE_NAME;
}
