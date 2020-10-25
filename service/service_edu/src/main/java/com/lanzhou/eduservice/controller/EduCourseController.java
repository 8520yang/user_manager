package com.lanzhou.eduservice.controller;


import com.lanzhou.commouils.R;
import com.lanzhou.eduservice.entity.EduCourse;
import com.lanzhou.eduservice.entity.vo.CourseInfoVo;
import com.lanzhou.eduservice.entity.vo.CoursePublishVo;
import com.lanzhou.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回kechengid
        String id=courseService.saveCourseInfo(courseInfoVo);

        return R.OK().data("courseId",id);
    }



    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo= courseService.publishCourseIInfo(id);
        return R.OK().data("publishCourse",coursePublishVo);
    }


    //课程发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.OK();
    }

    @GetMapping("findCourse")
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.OK().data("list",list);
    }


}

