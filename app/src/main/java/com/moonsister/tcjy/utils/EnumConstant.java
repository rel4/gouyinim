package com.moonsister.tcjy.utils;

/**
 * Created by jb on 2016/8/11.
 */
public class EnumConstant {
    /**
     * type 动态类型 1红包图集，2普通图文，3普通小视频动态，4免费语音，5付费语音，6付费视频
     *
     * @return
     */
    public enum DynamicType {
        CHARGE_PIC(1), FREE_PIC(2), FREE_VIDEO(3), FREE_VOICE(4), CHARGE_VOICE(5), CHARGE_VIDEO(6);


        private final int type;

        private DynamicType(int type) {
            this.type = type;
        }

        public int getValue() {
            return type;
        }
    }
}
