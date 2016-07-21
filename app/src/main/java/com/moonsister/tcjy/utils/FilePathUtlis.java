package com.moonsister.tcjy.utils;


import java.io.File;

/**
 * Created by jb on 2016/6/17.
 */
public class FilePathUtlis {
    /**
     * 获取上传的路径
     * /mm/20160617/15/时间戳+随机几位数.jpg
     *
     * @param fileType
     * @return
     */
    public static String getUpAliyunFilePath(FileType fileType) {
        String path = TimeUtils.getCurrentYearMonthDay() + File.separator + TimeUtils.getCurrentHour() + File.separator + TimeUtils.getCurrentTimestamp() + NumberUtlis.getRandomNumber(4) + fileType.getSuffixName();

        return path;
    }

    public enum FileType {

        JPG("image", ".jpg"), MP4("video", ".mp4");
        private String suffixName;
        private String fileFormat;

        private FileType(String fileFormat, String suffixName) {
            this.suffixName = suffixName;
            this.fileFormat = fileFormat;
        }

        public String getFileFormat() {
            return fileFormat;
        }

        public void setFileFormat(String fileFormat) {
            this.fileFormat = fileFormat;
        }

        public String getSuffixName() {
            return suffixName;
        }

        public void setSuffixName(String suffixName) {
            this.suffixName = suffixName;
        }
    }

}
