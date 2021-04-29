package com.lingmeng.qiniu;

import com.lingmeng.cache.JedisUtil;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author skin
 * @Date  2021/1/28 14:20
 * @description 七牛云工具类
 **/
@Component
public class QiNiuUtils {

    private static QiNiuConfig qiNiuConfig;

    @Autowired
    public void setQiNiuConfig(QiNiuConfig qiNiuConfig){
        QiNiuUtils.qiNiuConfig=qiNiuConfig;
    }

    /**
     * @author skin
     * @param fileName	 例："公司/存储/qiniu.jpg";
     * @Date  2021/1/28 14:20
     * @description 获取七牛云私有空间下载文件链接
     **/
    public static String getDownLoadUrl(String fileName) {
        try {
            Object obj = JedisUtil.getObject(fileName);
            if (obj != null) {
                return (String) obj;
            }
            String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", qiNiuConfig.getProtocol() + "://" + qiNiuConfig.getDomain(), encodedFileName);
            Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
            //1小时，可以自定义链接过期时间
            long expireInSeconds = 3600;
            String url = auth.privateDownloadUrl(publicUrl, expireInSeconds);
            JedisUtil.setObject(fileName, url, (int) expireInSeconds);
            return url;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    /**
     * 获取七牛云3D私有空间下载文件链接
     *
     * @param fileName 例："公司/存储/qiniu.jpg";
     * @return
     * @throws Exception
     */
    public static String getRealFaceDownLoadUrl(String fileName) {
        try {
            Object obj = JedisUtil.getObject(fileName);
            if (obj != null) {
                return (String) obj;
            }
            String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", qiNiuConfig.getRealFaceProtocol() + "://" + qiNiuConfig.getRealFaceDomain(), encodedFileName);
            Auth auth = Auth.create(qiNiuConfig.getRealFaceAccessKey(), qiNiuConfig.getRealFaceSecretKey());
            //1小时，可以自定义链接过期时间
            long expireInSeconds = 3600;
            String url= auth.privateDownloadUrl(publicUrl, expireInSeconds);
            JedisUtil.setObject(fileName,url,(int)expireInSeconds);
            return url;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    /**
     * 获取七牛云3D私有空间下载文件链接
     *
     * @param fileName 例："公司/存储/qiniu.jpg";
     * @return
     * @throws Exception
     */
    public static String getRealFaceDownLoadUrlToC(String fileName) {
        try {
            String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", qiNiuConfig.getRealFaceProtocol() + "://" + qiNiuConfig.getRealFaceDomain(), encodedFileName);
            Auth auth = Auth.create(qiNiuConfig.getRealFaceAccessKey(), qiNiuConfig.getRealFaceSecretKey());
            //1小时，可以自定义链接过期时间
            long expireInSeconds = 3600;
            String url= auth.privateDownloadUrl(publicUrl, expireInSeconds);
            JedisUtil.setObject(fileName,url,(int)expireInSeconds);
            return url;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 独立版本&& ToC版本的七牛云
     * @param fileName
     * @return
     */
    public static String getNeuronDownUrlByCustom(String fileName,String domain){
        return getDownLoadUrlByCustom(qiNiuConfig.getRealFaceAccessKey(),qiNiuConfig.getRealFaceSecretKey(),qiNiuConfig.getRealFaceProtocol(),domain,fileName,3600);
    }

    /**
     * 用户版 照片模型封面
     * @param fileName
     * @return
     */
    public static String getNeuronDownUrlByCustomPhoto(String fileName){
        return getDownLoadUrlByCustom(qiNiuConfig.getRealFaceAccessKey(),qiNiuConfig.getRealFaceSecretKey(),qiNiuConfig.getRealFaceProtocol(),qiNiuConfig.getRealFaceDomain(),fileName,3600);
    }
    /**
     * 医院版本的 七牛云
     * @param fileName
     * @return
     */
    public static String getHospitalDownUrlByCustom(String fileName,String domain){
        return getDownLoadUrlByCustom(qiNiuConfig.getAccessKey(),qiNiuConfig.getSecretKey(),qiNiuConfig.getProtocol(),domain,fileName,3600);
    }

    /**
     * 获取七牛云3D私有空间下载文件链接
     *
     * @param fileName 例："公司/存储/qiniu.jpg";
     * @return
     * @throws Exception
     */
    public static String getUserApp3DDownLoadUrl(String fileName) {
            //1小时，可以自定义链接过期时间
            int expireInSeconds = 3600;
            return getDownLoadUrlByCustom(qiNiuConfig.getAccessKey(),qiNiuConfig.getSecretKey(),qiNiuConfig.getProtocol(),qiNiuConfig.getAppModelDomain(),fileName,expireInSeconds);
    }

    /**
     * 获取七牛云私有空间下载文件链接
     * 用户自定义参数，没有过期时间，不缓存
     *
     * @param fileName 例："公司/存储/qiniu.jpg";
     * @return
     * @throws Exception
     */
    public static String getDownLoadUrlByCustomNoExpire(String accessKey,String secretKey,String protocol,String domain,String fileName) {
        try {

            String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", protocol + "://" + domain, encodedFileName);
            Auth auth = Auth.create(accessKey, secretKey);
            //1小时，可以自定义链接过期时间
            String url = auth.privateDownloadUrl(publicUrl);
            return url;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 获取七牛云私有空间下载文件链接
     * 用户自定义配置参数
     *
     * @param fileName "公司/存储/qiniu.jpg"; 例："/neuron-qiniu/photos/uuid.png"
     * @param accessKey
     * @param secretKey
     * @param protocol
     * @param domain
     * @param expireInSeconds 过期时间 秒
     * @return
     * @throws Exception
     */
    public static String getDownLoadUrlByCustom(String accessKey,String secretKey,String protocol,String domain,String fileName,int expireInSeconds) {
        try {
            Object obj = JedisUtil.getObject(fileName);
            if (obj != null) {
                return (String) obj;
            }
            String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", protocol + "://" + domain, encodedFileName);
            Auth auth = Auth.create(accessKey, secretKey);
            //1小时，可以自定义链接过期时间
            String url = auth.privateDownloadUrl(publicUrl, expireInSeconds);
            JedisUtil.setObject(fileName, url, expireInSeconds);
            return url;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 判断是否是七牛云的图片
     *
     * @param url
     * @return
     */
    public static boolean isQiNiu(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        return url.startsWith("/neuron-qiniu");
    }

//    public static void main(String[] args) {
//        String s = getDownLoadUrl("ZsEb0bCr6tW8WjB0UQnRfEpsAtxM55NrCVQ2Xnh0", "3UsOYjqMKgWLZ1BRQk5ywSrai8OpmED2VR7TS11T", "/neuron-qiniu/photos/1A3D80F3-B162-4C48-9444-D6136A250247.png", "cdn.3d.neurongenius.com","http");
//        System.out.println(s);
//    }

}
