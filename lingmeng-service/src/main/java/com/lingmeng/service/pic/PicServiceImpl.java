package com.lingmeng.service.pic;


import com.lingmeng.api.pic.picService;
import com.lingmeng.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class PicServiceImpl implements picService {


    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");

    @Override
    public String upload(MultipartFile file) {
        // 1、图片信息校验
        // 1)校验文件类型

        try {
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                log.info("上传失败，文件类型不匹配：{}", type);
                throw  new RestException("上传失败，文件类型不匹配");
            }
            // 2)校验图片内容
            BufferedImage image = null;
            image = ImageIO.read(file.getInputStream());
            if (image == null) {
                log.info("上传失败，文件内容不符合要求");
                throw  new RestException("上传失败，文件内容不符合要求");
            }
            // 2、保存图片
            // 2.1、生成保存目录
            File dir = new File("D:\\tem_pic");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 2.2、保存图片
            file.transferTo(new File(dir, file.getOriginalFilename()));

            // 2.3、拼接图片地址
            String url = "http://image.lingmeng.com/upload/" + file.getOriginalFilename();
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
