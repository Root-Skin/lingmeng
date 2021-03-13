package com.lingmeng.controller.qiniu;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.lingmeng.base.RestReturn;
import com.lingmeng.exception.RestException;
import com.lingmeng.qiniu.QiNiuConfig;
import com.lingmeng.qiniu.QiNiuUtils;
import com.lingmeng.qiniu.QiniuFactory;
import com.lingmeng.service.qiniu.QiniuService;
import com.qiniu.util.Auth;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author skin
 * @Date 2021/1/28 14:29
 * @description 七牛上传
 **/
@RestController
@RequestMapping("/qiniu")
public class QiniuController extends ApiController {

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    QiNiuConfig qiNiuConfig;

    @Autowired
    QiniuFactory qiniuFactory;

    /**
     * 获取七牛上传图片的token
     *
     * @param endUrl  3d: 上传 文件名称（例如：crop.png） 其他：上传文件类型(例如：png,jpg等)
     * @param baseUrl 3d: 上传 "/neuron-qiniu/3d/"    其他：文件上传文件分类名称，例如：/neuron-qiniu/photos/ (图片文件)、/neuron-qiniu/files/ (文件)等
     * @param uuid    前端需要覆盖，则使用前端上传的uuid 进行文件的覆盖
     * @return
     */
    @GetMapping("/uptoken")
    public RestReturn getUpToken(@RequestParam String endUrl, @RequestParam String baseUrl, @RequestParam(required = false) String uuid) {
        if (StringUtils.isEmpty(endUrl) || StringUtils.isEmpty(baseUrl)) {
            throw new RestException("参数异常");
        }
        String key;
        String token;
        //上传3D模型数据到私有云
        Map<String, Object> map = new HashMap<>();
        //目录不为空(并且是3d的目录)
        if (StringUtils.isNotEmpty(baseUrl) && "/neuron-qiniu/3d/".equals(baseUrl)) {
            if (StringUtils.isNotEmpty(uuid)) {
                key = uuid;
            } else {
                key = UUID.randomUUID().toString().toUpperCase();
            }
            key = key + "/" + endUrl;
            Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
            //使用覆盖上传
            token = auth.uploadToken(qiNiuConfig.getAppModelBucket(), key);
            final String downLoadUrl;
            downLoadUrl = QiNiuUtils.getUserApp3DDownLoadUrl(key);
            map.put("path", downLoadUrl);
        } else {
            if (StringUtils.isNotEmpty(uuid)) {
                key = uuid;
            } else {
                key = UUID.randomUUID().toString().toUpperCase();
            }
            if (StringUtils.isEmpty(baseUrl)) {
                key = key + "." + endUrl;
            } else {
                //如果前端指定了要上传到某一个目录中去(不是3d)
                key = baseUrl + key + "." + endUrl;
            }

            Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
            token = auth.uploadToken(qiNiuConfig.getBucket(), key);

            final String downLoadUrl;
            downLoadUrl = QiNiuUtils.getDownLoadUrl(key);
            map.put("path", downLoadUrl);
        }

        map.put("token", token);
        map.put("key", key);

        return RestReturn.ok(map);
    }

    /**
     * 第二个版本的七牛上传
     *
     * @param endUrl 3d: 上传 文件名称（例如：crop.png） 其他：上传文件类型(例如：png,jpg等)
     * @param uuid   前端需要覆盖，则使用前端上传的uuid 进行文件的覆盖
     * @param type   获取七牛的信息的类型 @link{QiniuTypeEnum}
     * @return
     */
    @GetMapping("uptokenV2")
    public RestReturn getupTokenV2(@RequestParam String endUrl, @RequestParam(required = false) String uuid, @RequestParam Integer type) {
        if (StringUtils.isEmpty(endUrl)) {
            throw new RestException("参数异常");
        }
        return RestReturn.ok(QiniuFactory.getQiniuFactory().getQinuData(type, endUrl, uuid));
    }

    /**
     * 获取七牛云下载文件的路径
     *
     * @param fileNames
     * @return
     */
    @PostMapping("/geturl")
    public RestReturn getUrl(@RequestBody List<String> fileNames) {

        if (CollectionUtils.isNotEmpty(fileNames)) {
            List<String> downLoadUrlList = new ArrayList<>();
            if (fileNames.size() > 1) {
                for (String fileName : fileNames) {
                    final String downLoadUrl = QiNiuUtils.getDownLoadUrl(fileName);
                    downLoadUrlList.add(downLoadUrl);
                }
                return RestReturn.ok(10000, downLoadUrlList,"操作成功" );
            } else {
                final String downLoadUrl = QiNiuUtils.getDownLoadUrl(fileNames.get(0));
                downLoadUrlList.add(downLoadUrl);
                return RestReturn.ok(10000, downLoadUrlList,"操作成功" );
            }
        } else {
            throw new RestException("文件名称不能为空");
        }


    }

    /**
     * 获取七牛云下载文件的路径
     *
     * @param fileNames
     * @return
     */
    @PostMapping("/getThreeDimensionalUrl")
    public RestReturn getThreeDimensionalUrl(@RequestBody List<String> fileNames) {

        if (CollectionUtils.isNotEmpty(fileNames)) {
            List<String> downLoadUrlList = new ArrayList<>();
            if (fileNames.size() > 1) {
                for (String fileName : fileNames) {
                    final String downLoadUrl = QiNiuUtils.getUserApp3DDownLoadUrl(fileName);
                    downLoadUrlList.add(downLoadUrl);
                }
                return RestReturn.ok(10000, downLoadUrlList,"操作成功");
            } else {
                final String downLoadUrl = QiNiuUtils.getUserApp3DDownLoadUrl(fileNames.get(0));
                downLoadUrlList.add(downLoadUrl);
                return RestReturn.ok(10000, downLoadUrlList,"操作成功" );
            }
        } else {
            throw new RestException("文件名称不能为空");
        }
    }
}
