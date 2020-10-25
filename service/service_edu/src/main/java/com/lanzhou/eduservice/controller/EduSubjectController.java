package com.lanzhou.eduservice.controller;


import com.lanzhou.commouils.R;
import com.lanzhou.eduservice.entity.subject.OneSubject;
import com.lanzhou.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-23
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来的文件，读取
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.OK();
    }


    //课程分类的列表功能
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list=subjectService.getAllOneTwoSubject();
        return R.OK().data("list",list);
    }
}

