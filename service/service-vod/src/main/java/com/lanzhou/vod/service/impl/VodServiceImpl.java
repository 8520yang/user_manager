package com.lanzhou.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lanzhou.commouils.R;
import com.lanzhou.vod.service.VodService;
import com.lanzhou.vod.utils.ConstantVodUtils;
import com.lanzhou.vod.utils.InitObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.invoke.ConstantCallSite;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {
        //fileName上传文件原始名称
        try {
            //01.mp4,在title中不带后缀名
            String fileName=file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream=file.getInputStream();
            //title：上传文件后的名字
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            String videoId=null;
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                videoId=response.getVideoId();

            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId=response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreVideo(List videoList) {
        try {
            DefaultAcsClient defaultAcsClient = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建一个删除视频的request
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            //设置视频id
            String join = StringUtils.join(videoList.toArray(), ",");

            deleteVideoRequest.setVideoIds(join);
            //调用方法实现删除
            defaultAcsClient.getAcsResponse(deleteVideoRequest);

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
