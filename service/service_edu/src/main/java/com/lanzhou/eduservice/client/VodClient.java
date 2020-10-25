package com.lanzhou.eduservice.client;

import com.lanzhou.commouils.R;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name="service-vod",fallback = VoidClientImpl.class)
public interface VodClient {
    //定义调用的方法的路径
    //根据视频id删除阿里云的视频
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);


}
