package com.gouyin.im.aliyun;

import android.provider.CalendarContract;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.LogUtils;

/**
 * Created by jb on 2016/6/16.
 */
public class AliyunManager {
    private OSS oss;

    // 运行sample前需要配置以下字段为有效的值
    private static final String endpoint = "http://mimei.oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "qfNP5fs6r3MYzYy7";
    private static final String accessKeySecret = "qfNP5fs6r3MYzYy7";


    private static final String testBucket = "mimei";
    private static final String uploadObject = "mm";
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

    public void upLoadFile(String uploadFilePath, onAliyunListener listener) {
        LogUtils.e(this,  "uploadFilePath : " + uploadFilePath);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(testBucket, uploadObject, uploadFilePath);

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

                LogUtils.e(this, "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.e(this, "UploadSuccess : ");
                LogUtils.e(this, "ETag : " + result.getETag());
                LogUtils.e(this, "RequestId : " + result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常


                    LogUtils.e(this, "ErrorCode : " + serviceException.getErrorCode());
                    LogUtils.e(this, "RequestId : " + serviceException.getRequestId());
                    LogUtils.e(this, "HostId : " + serviceException.getHostId());
                    LogUtils.e(this, "RawMessage : " + serviceException.getRawMessage());

                }
            }
        });


    }


    public interface onAliyunListener<T> {
        void onResuit(boolean isSucced, T t);
    }
}
