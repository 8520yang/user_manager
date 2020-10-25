package com.lanzhou.eduservice.controller;


import com.lanzhou.commouils.R;
import com.lanzhou.eduservice.client.VodClient;
import com.lanzhou.eduservice.entity.EduVideo;
import com.lanzhou.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    public EduVideoService eduVideoService;
    @Autowired
    public VodClient vodClient;



    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.OK();
    }

    //删除小节

    //删除小节的时候还要删除视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (videoSourceId.length()!=0) {
            vodClient.removeAlyVideo(videoSourceId);
        }
        eduVideoService.removeById(id);
        return R.OK();
    }

}

