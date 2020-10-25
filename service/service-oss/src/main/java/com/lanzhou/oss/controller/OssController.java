package com.lanzhou.oss.controller;

import com.lanzhou.commouils.R;
import com.lanzhou.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file)
    {
        //返回路径
        String url=ossService.uploadFileAvatar(file);

        //获取上传的文件
        return R.OK().data("url",url);
    }
}
