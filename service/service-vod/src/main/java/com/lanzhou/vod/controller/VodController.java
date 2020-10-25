package com.lanzhou.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lanzhou.commouils.R;
import com.lanzhou.vod.service.VodService;
import com.lanzhou.vod.utils.ConstantVodUtils;
import com.lanzhou.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;
    //上传视频到阿里云
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file)
    {
        //返回上传视频的id
        String videoId =vodService.uploadVideo(file);
        return R.OK().data("videoId",videoId);
    }


    //根据视频id删除阿里云的视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try {
            DefaultAcsClient defaultAcsClient = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建一个删除视频的request
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            //设置视频id
            deleteVideoRequest.setVideoIds(id);
            //调用方法实现删除
            defaultAcsClient.getAcsResponse(deleteVideoRequest);
            return R.OK();
        }catch (Exception e){
            e.printStackTrace();
            return R.ERROR();
        }

    }



    //删除多个视频的方法
    //参数是多个视频id
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoList") List videoList){
        vodService.removeMoreVideo(videoList);
        return R.OK();
    }



    //获取视频凭证
    //根据视频id获取凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try{
            DefaultAcsClient defaultAcsClient = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //设置id
            request.setVideoId(id);
            //得到凭证
            GetVideoPlayAuthResponse acsResponse = defaultAcsClient.getAcsResponse(request);
            String playAuth = acsResponse.getPlayAuth();
            return R.OK().data("playAuth",playAuth);
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
            return null;
        }
    }
}
