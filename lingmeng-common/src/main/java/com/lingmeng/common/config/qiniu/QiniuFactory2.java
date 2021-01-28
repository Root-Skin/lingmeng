package com.lingmeng.common.config.qiniu;

import com.lingmeng.common.utils.spring.SpringContextHolder;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author skin
 * @createTime 2021年01月27日
 * @Description 七牛工厂封装
 */
public class QiniuFactory2 {

    private QiNiuConfig qiNiuConfig;


    @Autowired
    public void setQiNiuConfig(QiNiuConfig qiNiuConfig){
        this.qiNiuConfig = qiNiuConfig;
    }

    private void init()  {
        final Method[] methods = QiniuFactory.class.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            final Method method = methods[i];
            if(method.isAnnotationPresent(Qiniu.class) && method.getAnnotation(Qiniu.class) != null){
                final AbstractTemplate template = (AbstractTemplate)method.invoke(this, new Object[]{});
                data.put(template.getType(),template);
                Collections.unmodifiableMap(data);
            }
        }
    }

    private boolean checkBeforeInit(){
        return data.size() == 0;
    }

    private ConcurrentHashMap<Integer,AbstractTemplate> data = new ConcurrentHashMap();

    /**
     * 单例，获取对象调用需要判空
     * @return
     */
    public static QiniuFactory getQiniuFactory(){
        QiniuFactory qiniuFactory = SpringContextHolder.getBean("qiniuFactory");
        if(qiniuFactory.checkBeforeInit()){
            try {
                qiniuFactory.init();
            } catch (Exception e) {
                return null;
            }
        }
        return qiniuFactory;
    }

    public Map<String,Object> getQinuData(Integer type, String endUrl, String uuid){
        return data.get(type).getQiniuInfo(endUrl,uuid);
    }

    /**
     * neuron-3d 独立版
     * @return
     */
    @Qiniu
    private AbstractTemplate get3D() {
        return new AbstractTemplate(QiniuTypeEnum.NEURON_THREE_DIMENSIONAL) {
            @Override
            Map<String, Object> getQiniuInfo(String endUrl,String uuid) {
                Map<String,Object> map = new HashMap<>();
                String key;
                String token;
                if (ToolUtil.isNotEmpty(uuid)) {
                    key = uuid;
                } else {
                    key = UUID.randomUUID().toString().toUpperCase();
                }
                key = key + "/" + endUrl;
                Auth auth = Auth.create(qiNiuConfig.getRealFaceAccessKey(), qiNiuConfig.getRealFaceSecretKey());
                token = auth.uploadToken(qiNiuConfig.getRealFaceBucket(), key);
                final String downLoadUrl = QiNiuUtils.getNeuronDownUrlByCustom(key,qiNiuConfig.getRealFaceDomain());
                map.put("path", downLoadUrl);
                map.put("token",token);
                map.put("key",key);
                return map;
            }
        };
    }

    /**
     * 录音文件
     */
    @Qiniu
    private AbstractTemplate getAudio(){
        return new AbstractTemplate(QiniuTypeEnum.AUDIO) {
            @Override
            Map<String, Object> getQiniuInfo(String endUrl,String uuid) {
                return null;
            }
        };
    }

    /**App
     * 批量处理文件
     * @return
     */
    @Qiniu
    private AbstractTemplate getAudioList(){
        return new AbstractTemplate(QiniuTypeEnum.AUDIO_LIST) {
            @Override
            Map<String, Object> getQiniuInfo( String endUrl, String uuid) {
                return null;
            }
        };
    }

    /**
     * 附件
     * @return
     */
    @Qiniu
    private AbstractTemplate getEnclosure(){
        return new AbstractTemplate(QiniuTypeEnum.ENCLOSURE) {
            @Override
            Map<String, Object> getQiniuInfo(String endUrl,String uuid) {
                return null;
            }
        };
    }

    /**
     * 客户照片
     * @return
     */
    @Qiniu
    private AbstractTemplate getPhoto(){
        return new AbstractTemplate(QiniuTypeEnum.PHOTO) {
            @Override
            Map<String, Object> getQiniuInfo(String endUrl,String uuid) {
                if (!systemConfig.getPhoto().getQiNiuUsed()) {
                    throw new BussinesException("数据为空", 10002);
                }
                Map<String,Object> map = new HashMap<>();
                String key ;
                String token;
                if (ToolUtil.isNotEmpty(uuid)) {
                    key = QiniuTypeEnum.PHOTO_BASEURL + uuid;
                } else {
                    key = QiniuTypeEnum.PHOTO_BASEURL + UUID.randomUUID().toString().toUpperCase();
                }
                key = key + "." + endUrl;
                Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
                token = auth.uploadToken(qiNiuConfig.getBucket(), key);
                final String downLoadUrl = QiNiuUtils.getHospitalDownUrlByCustom(key,qiNiuConfig.getDomain());
                map.put("path", downLoadUrl);
                map.put("token",token);
                map.put("key",key);
                return map;
            }
        };
    }

    /**
     * 用户私有3D
     * @return
     */
    @Qiniu
    private AbstractTemplate getUser3D(){
        return new AbstractTemplate(QiniuTypeEnum.THREE_DIMENSIONAL) {
            @Override
            Map<String, Object> getQiniuInfo(String endUrl,String uuid) {
                Map<String,Object> map = new HashMap<>();
                String key ;
                String token;
                if (ToolUtil.isNotEmpty(uuid)) {
                    key = QiniuTypeEnum.PHOTO_BASEURL + uuid;
                } else {
                    key = QiniuTypeEnum.PHOTO_BASEURL + UUID.randomUUID().toString().toUpperCase();
                }
                key = key + "/" + endUrl;
                Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
                token = auth.uploadToken(qiNiuConfig.getAppModelBucket(), key);
                final String downLoadUrl = QiNiuUtils.getHospitalDownUrlByCustom(key,qiNiuConfig.getAppModelDomain());
                map.put("path", downLoadUrl);
                map.put("token",token);
                map.put("key",key);
                return map;
            }
        };
    }

    /**
     * App 3D
     * @return
     */
    @Qiniu
    private AbstractTemplate getApp3D(){
        return new AbstractTemplate(QiniuTypeEnum.APP_THREE_DIMENSIONAL) {
            @Override
            Map<String, Object> getQiniuInfo(String endUrl,String uuid) {
                return null;
            }
        };
    }
}
