package com.lingmeng.api.pic;


import org.springframework.web.multipart.MultipartFile;

public interface picService {

     String upload(MultipartFile file);
}
