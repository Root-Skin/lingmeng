package com.lingmeng.service.pic;


import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.lingmeng.api.pic.picService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class PicServiceImpl implements picService {

    private static final Logger logger = LoggerFactory.getLogger(PicServiceImpl.class);
    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg","image/jpg");

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String upload(MultipartFile file) {
        // 1、图片信息校验
        // 1)校验文件类型


            try {
                // 1、图片信息校验
                // 1)校验文件类型
                String type = file.getContentType();
                if (!suffixes.contains(type)) {
                    logger.info("上传失败，文件类型不匹配：{}", type);
                    return null;
                }
                // 2)校验图片内容
                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image == null) {
                    logger.info("上传失败，文件内容不符合要求");
                    return null;
                }

                // 2、将图片上传到FastDFS
                // 2.1、获取文件后缀名
                String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
                // 2.2、上传
                StorePath storePath = this.storageClient.uploadFile(
                        file.getInputStream(), file.getSize(), extension, null);
                // 2.3、返回完整路径
                return "http://image.lingmeng.com/" + storePath.getFullPath();
            } catch (Exception e) {
                return null;
            }
        }
}
