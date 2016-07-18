package com.moonsister.im.manager;

import com.moonsister.im.CacheManager;
import com.moonsister.im.bean.PersonInfoDetail;
import com.moonsister.im.utils.ConfigUtils;
import com.moonsister.im.utils.StringUtis;

import io.rong.imkit.RongyunConfig;


/**
 * Created by jb on 2016/6/30.
 */
public class UserInfoManager {
    private volatile static UserInfoManager instance;
    private static PersonInfoDetail info;


    public static UserInfoManager getInstance() {
        if (instance == null) {
            synchronized (UserInfoManager.class) {
                if (instance == null) {
                    initFilePersonInfoDetail();
                    instance = new UserInfoManager();
                }

            }
        }
        return instance;
    }

    /**
     * 初始化对象
     */
    private static void initFilePersonInfoDetail() {
        info = getFilePersonInfoDetail();
    }

    /**
     * 从本地获取信息对象
     *
     * @return
     */
    private static PersonInfoDetail getFilePersonInfoDetail() {
        PersonInfoDetail persinInfo = null;
        if (CacheManager.isExist4DataCache(ConfigUtils.getInstance().getApplicationContext(), CacheManager.CachePath.PERSON_INFODETAIL_INSTANCE)) {
            Object o = CacheManager.readObject(ConfigUtils.getInstance().getApplicationContext(), CacheManager.CachePath.PERSON_INFODETAIL_INSTANCE);
            if (o != null && o instanceof PersonInfoDetail) {
                persinInfo = (PersonInfoDetail) o;
            }
        } else {
            persinInfo = new PersonInfoDetail();
            CacheManager.saveObject(ConfigUtils.getInstance().getApplicationContext(), persinInfo, CacheManager.CachePath.PERSON_INFODETAIL_INSTANCE);
        }
        if (persinInfo == null) {
            persinInfo = new PersonInfoDetail();
            CacheManager.saveObject(ConfigUtils.getInstance().getApplicationContext(), persinInfo, CacheManager.CachePath.PERSON_INFODETAIL_INSTANCE);
        }
        return persinInfo;
    }

    /**
     * 保存到文件
     *
     * @param instance
     */
    private void saveFileInstance(PersonInfoDetail instance) {
        CacheManager.saveObject(ConfigUtils.getInstance().getApplicationContext(), instance, CacheManager.CachePath.PERSON_INFODETAIL_INSTANCE);
    }


    /**
     * 从内存获取信息对象
     *
     * @return
     */
    public PersonInfoDetail getMemoryPersonInfoDetail() {

        return info;
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return info.isLogin();
    }

    /**
     * 获取authcode
     *
     * @return
     */
    public String getAuthcode() {
        String authcode = info.getAuthcode();
        if (StringUtis.isEmpty(authcode))
            return "";
        return authcode;
    }

    /**
     * 获取融云的key
     *
     * @return
     */
    public String getRongyunKey() {
        String rongyunkey = info.getRongyunkey();
        if (StringUtis.isEmpty(rongyunkey))
            return "";
        return rongyunkey;
    }

    /**
     * 下线
     */
    public void logout() {
        saveFileInstance(null);
        initFilePersonInfoDetail();
        RongyunConfig.getInstance().offline();
    }

    /**
     * 用户头像
     *
     * @return
     */
    public String getAvater() {
        return info.getFace();
    }

    /**
     * 保存在内存
     *
     * @param instance
     */
    public void saveMemoryInstance(PersonInfoDetail instance) {
        info = instance;
        saveFileInstance(info);

    }

    /**
     * 认证状态
     *
     * @return
     */

    public int getCertificationStatus() {
        return info.getAttestation();
    }

    public String getUserSex() {
        return info.getSex();
    }

    public String getNickeName() {
        return info.getNickname();
    }
}
