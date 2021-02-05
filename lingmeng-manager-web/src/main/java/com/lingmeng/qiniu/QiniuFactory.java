package com.lingmeng.qiniu;


import com.lingmeng.common.utils.spring.SpringContextHolder;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author skin
 * @Date  2021/1/27 20:12
 * @description 七牛
 **/
@Component
@DependsOn("springContextHolder")  // 获取spring上下文
public class QiniuFactory {

    @Autowired
    private QiNiuConfig qiNiuConfig;




    private  static ConcurrentHashMap<Integer, AbstractTemplate> data = new ConcurrentHashMap();

    /**
     * @author skin
     * @param
     * @Date  2021/1/28 13:45
     * @description 单例,获取对象变null.在初始化之前调用
     **/
    public static QiniuFactory getQiniuFactory(){
        QiniuFactory qiniuFactory = SpringContextHolder.getBean("qiniuFactory");
        if(data.size() == 0){
            try {
                qiniuFactory.init();
            } catch (Exception e) {
                return null;
            }
        }
        return qiniuFactory;
    }

    /**
     * @author skin
     * @param
     * @Date  2021/1/28 13:46
     * @description  初始化方法
     **/
    private void init() throws Exception{
        final Method[] methods = QiniuFactory.class.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            final Method method = methods[i];
            if(method.isAnnotationPresent(Qiniu.class) && method.getAnnotation(Qiniu.class) != null){
                final AbstractTemplate template = (AbstractTemplate)method.invoke(this, new Object[]{});
                data.put(template.getType(),template);
                Collections.unmodifiableMap(data);  //返回不可变的副本
            }
        }
    }

    public  Map<String,Object> getQinuData(Integer type,String endUrl,String uuid){
        return data.get(type).getQiniuInfo(endUrl,uuid);
    }


    @Qiniu
    private AbstractTemplate getUser3D(){
        return new AbstractTemplate(QiniuTypeEnum.THREE_DIMENSIONAL) {
            @Override
            Map<String, Object> getQiniuInfo(String endUrl,String uuid) {
                Map<String,Object> map = new HashMap<>();
                String key ;
                String token;
                if (StringUtils.isNotEmpty(uuid)) {
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
    }  @Qiniu
    private AbstractTemplate getPhoto(){
        return new AbstractTemplate(QiniuTypeEnum.PHOTO) {
            @Override
            Map<String, Object> getQiniuInfo(String endUrl,String uuid) {
                Map<String,Object> map = new HashMap<>();
                String key ;
                String token;
                if (StringUtils.isNotEmpty(uuid)) {
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
    @Qiniu
    private AbstractTemplate get3D() {
        return new AbstractTemplate(QiniuTypeEnum.NEURON_THREE_DIMENSIONAL) {
            @Override
            Map<String, Object> getQiniuInfo(String endUrl,String uuid) {
                Map<String,Object> map = new HashMap<>();
                String key;
                String token;
                if (StringUtils.isNotEmpty(uuid)) {
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

}
