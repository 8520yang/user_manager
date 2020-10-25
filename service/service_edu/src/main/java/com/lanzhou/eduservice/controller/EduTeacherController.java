package com.lanzhou.eduservice.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.commouils.R;
import com.lanzhou.eduservice.entity.EduCourse;
import com.lanzhou.eduservice.entity.EduTeacher;
import com.lanzhou.eduservice.entity.vo.TeacherQuery;
import com.lanzhou.eduservice.service.EduCourseService;
import com.lanzhou.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@RestController
@CrossOrigin
@RequestMapping(value="/eduservice/edu-teacher",produces = "application/json;charset=utf-8")
public class EduTeacherController {
    //查询所有讲师的数据
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService eduCourseService;
    //rest风格，每种操作请求方式不一样
    @GetMapping("findAll")
    public R findAllTeacher()
    {
        //调用service方法，查询所有
        List<EduTeacher> list = teacherService.list(null);
        return R.OK().data("items",list);
    }


    //逻辑删除讲师的方法
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id)
    {
        boolean b = teacherService.removeById(id);
        if (b)
        {
            return R.OK();
        }else{
            return R.ERROR();
        }

    }

    //分页查询讲师方法
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit)
    {
        //创建page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        //调用方法实现分页
        //调用方法的时候底层会给我们做成封装，封装到pageTeracher
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.OK().data("total",total).data("rows",records);

    }

    //条件查询带分页
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery)
    {
        //创建一个page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);


        //构建条件
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        String name=teacherQuery.getName();
        Integer level=teacherQuery.getLevel();
        String begin=teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!(StringUtils.isEmpty(name)))
        {
            //构建条件
            eduTeacherQueryWrapper.like("name",name);

        }
        if (!(StringUtils.isEmpty(level)))
        {
            eduTeacherQueryWrapper.eq("level",level);
        }
        if (!(StringUtils.isEmpty(begin)))
        {
            eduTeacherQueryWrapper.ge("gmt_create",begin);
        }
        if (!(StringUtils.isEmpty(end)))
        {
            eduTeacherQueryWrapper.le("gmt_modified",end);
        }
        //
        eduTeacherQueryWrapper.orderByDesc("gmt_create");
        teacherService.page(pageTeacher,eduTeacherQueryWrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.OK().data("total",total).data("rows",records);
    }
    //添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher)
    {
        boolean save = teacherService.save(eduTeacher);
        if (save)
        {
            return R.OK();
        }else
            {
                return R.ERROR();
            }
    }

    //根据ID查询的基本方法
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id )
    {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.OK().data("teacher",eduTeacher);
    }

    //修改讲师的功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher)
    {
        boolean b = teacherService.updateById(eduTeacher);
        if (b)
        {
            return R.OK();
        }
        else
            {
                return R.ERROR();
            }
    }


    //查询讲师详情
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        //查询讲师和课程基本信息
        EduTeacher byId = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = eduCourseService.list(wrapper);
        return R.OK().data("teacher",byId).data("courseList",list);

    }
}

