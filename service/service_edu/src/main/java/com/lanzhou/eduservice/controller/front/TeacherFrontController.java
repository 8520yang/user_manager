package com.lanzhou.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.commouils.R;
import com.lanzhou.eduservice.entity.EduTeacher;
import com.lanzhou.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    //分页查询讲师的方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> teacherPage=new Page<>(page,limit);
        Map<String,Object> map=teacherService.getTeacherFrontList(teacherPage);
        //返回所有的数据
        return R.OK().data(map);
    }
}
