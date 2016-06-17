package com.gouyin.im.aliyun;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.FilePathUtlis;
import com.gouyin.im.utils.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jb on 2016/6/16.
 */
public class AliyunManager {
    private OSS oss;

    // 运行sample前需要配置以下字段为有效的值
    private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "qfNP5fs6r3MYzYy7";
    private static final String accessKeySecret = "yrvj6Yv9Vm6ZcZFEgxaxOsPCcYy4Jy";


    private static final String bucket = "mimei";

    private static final String downloadObject = "sampleObject";
    private static AliyunManager instances;

    private AliyunManager() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(ConfigUtils.getInstance().getApplicationContext(), endpoint, credentialProvider, conf);
    }

    public static AliyunManager getInstance() {
        if (instances == null) {
            synchronized (AliyunManager.class) {
                if (instances == null)
                    return new AliyunManager();
                return instances;
            }
        }
        return instances;
    }

    public String upLoadFile(String uploadFilePath, FilePathUtlis.FileType fileType) throws ClientException, ServiceException {
        LogUtils.e(this, "uploadFilePath : " + uploadFilePath);
        // 构造上传请求
        String path = fileType.getFileFormat() + File.separator + FilePathUtlis.getUpAliyunFilePath(fileType);
        PutObjectRequest put = new PutObjectRequest(bucket, path, uploadFilePath);
//        try {

            PutObjectResult putResult = oss.putObject(put);

            LogUtils.e("PutObject", "UploadSuccess");
            String host = endpoint.substring(0, endpoint.indexOf("//") + 2) + put.getBucketName() + "." + endpoint.substring(endpoint.indexOf("//") + 2) + File.separator + put.getObjectKey();

            LogUtils.e(this, "ServerCallbackReturnBody : " + host);
            LogUtils.e("ETag", "ETag : " + putResult.getETag());
            LogUtils.e("RequestId", "  : " + putResult.getRequestId());

            return host;
//        } catch (ClientException e) {
//            // 本地异常如网络异常等
//
//            e.printStackTrace();
//
//        } catch (ServiceException e) {
//            // 服务异常
//            LogUtils.e("RequestId", e.getRequestId());
//            LogUtils.e("ErrorCode", e.getErrorCode());
//            LogUtils.e("HostId", e.getHostId());
//            LogUtils.e("RawMessage", e.getRawMessage());
//
//        }


    }


    public interface onAliyunListener<T> {
        void onResuit(boolean isSucced, T t);
    }

    public class AliyunCallBack {
        private String eTag;
        private String RequestId;
        private String serverFilePath;
    }
}
