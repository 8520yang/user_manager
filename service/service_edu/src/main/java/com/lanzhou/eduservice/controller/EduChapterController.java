package com.lanzhou.eduservice.controller;


import com.lanzhou.commouils.R;
import com.lanzhou.eduservice.entity.EduChapter;
import com.lanzhou.eduservice.entity.chapter.ChapterVo;
import com.lanzhou.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    //根据课程id来进行查询
    @GetMapping("getChapterVideo/{courseId}")
    //查询课程的章节小节
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list =eduChapterService.getChapterVideoByCourseId(courseId);
        return R.OK().data("allChapterVideo",list);
    }



    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.OK();
    }


    //修改方法
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter byId = eduChapterService.getById(chapterId);
        return R.OK().data("chapter",byId);
    }
    //先查询
    //在修改
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.OK();
    }


    //删除
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean b = eduChapterService.deleteChapter(chapterId);
        if (b){
            return R.OK();
        }
         else {
             return R.ERROR();
        }
    }

}

