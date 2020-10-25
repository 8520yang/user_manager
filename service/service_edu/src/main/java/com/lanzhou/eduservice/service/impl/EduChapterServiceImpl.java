package com.lanzhou.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanzhou.eduservice.entity.EduChapter;
import com.lanzhou.eduservice.entity.EduVideo;
import com.lanzhou.eduservice.entity.chapter.ChapterVo;
import com.lanzhou.eduservice.entity.chapter.VedioVo;
import com.lanzhou.eduservice.mapper.EduChapterMapper;
import com.lanzhou.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanzhou.eduservice.service.EduVideoService;
import com.lanzhou.servicebase.exceptionHandler.GlobalExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //查出所有章节
        QueryWrapper<EduChapter> eduChapterQueryWrapper=new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(eduChapterQueryWrapper);
        //所有小节
        QueryWrapper<EduVideo> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> list = eduVideoService.list(videoQueryWrapper);

        //遍历集合进行封装
        List<ChapterVo> finalList=new ArrayList<>();
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);
            List<VedioVo> viodeList=new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                EduVideo eduVideo = list.get(j);
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                   VedioVo eduVideo1 = new VedioVo();
                    //封装
                    BeanUtils.copyProperties(eduVideo,eduVideo1);
                    //放到小节封装的集合中
                    viodeList.add(eduVideo1);
                }
            }
            chapterVo.setChildren(viodeList);
        }
        //进行封装

        return finalList;
    }
    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节id，查询小结表，能查询出来就不删除，
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        //判断
        if (count>0){
            System.out.println("无法删除");
            return false;
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }
}
