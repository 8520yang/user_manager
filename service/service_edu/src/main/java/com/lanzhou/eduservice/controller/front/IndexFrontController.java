package com.lanzhou.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.commouils.R;
import com.lanzhou.eduservice.entity.EduCourse;
import com.lanzhou.eduservice.entity.EduTeacher;
import com.lanzhou.eduservice.service.EduCourseService;
import com.lanzhou.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indextfront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService teacherService;

    //查询
    @GetMapping("index")
    public R index(){
        //查询前八个热门课程
        QueryWrapper<EduCourse> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.orderByDesc("id");
        objectQueryWrapper.last("limit 8");
        List<EduCourse> list = eduCourseService.list(objectQueryWrapper);
        //前四个名师
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherQueryWrapper);
        return R.OK().data("eduList",list).data("teacherList",teacherList);
    }
}
