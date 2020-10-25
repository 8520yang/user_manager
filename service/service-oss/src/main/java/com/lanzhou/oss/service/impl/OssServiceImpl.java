package com.lanzhou.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lanzhou.oss.service.OssService;
import com.lanzhou.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;


@Service
public class OssServiceImpl implements OssService {


    //上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName=ConstantPropertiesUtils.BUCKET_NAME;
        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            originalFilename=uuid+originalFilename;

            //把我们的文件按照日期进行分类
            //2020/11/22/01.jpg
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            originalFilename=datePath+"/"+originalFilename;
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName,originalFilename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
            String url="";
            url = "http://" + bucketName + "." + endpoint + "/" + originalFilename;
            return url;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
