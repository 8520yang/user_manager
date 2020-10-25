package com.lanzhou.eduorder.client;

import com.lanzhou.commouils.orderVo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-edu")
public interface EduClient {
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseOrderVo getCourseInfoOrder(@PathVariable("id") String id);
}
